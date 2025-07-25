package dev.pantanal.catalogo.filmes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import dev.pantanal.catalogo.filmes.dto.FilmeDTO;
import dev.pantanal.catalogo.filmes.dto.FilmeCreateDTO;
import dev.pantanal.catalogo.filmes.dto.FilmeUpdateDTO;

@SpringBootTest
class FilmeServiceTest {

    @MockitoBean
    private FilmeRepository filmeRepository;

    @MockitoBean
    private FilmeMapper filmeMapper;

    @Autowired
    private FilmeService filmeService;

    @Test
    void listarTodos_deveRetornarListaDeFilmeDTO() {
        Filme filme1 = new Filme();
        filme1.setId(1L);
        filme1.setTitulo("Matrix");
        Filme filme2 = new Filme();
        filme2.setId(2L);
        filme2.setTitulo("Interestelar");
        List<Filme> filmes = Arrays.asList(filme1, filme2);
        FilmeDTO dto1 = new FilmeDTO();
        dto1.setId(1L);
        dto1.setTitulo("Matrix");
        FilmeDTO dto2 = new FilmeDTO();
        dto2.setId(2L);
        dto2.setTitulo("Interestelar");
        when(filmeRepository.findAll()).thenReturn(filmes);
        when(filmeMapper.toDTO(filme1)).thenReturn(dto1);
        when(filmeMapper.toDTO(filme2)).thenReturn(dto2);
        List<FilmeDTO> result = filmeService.listarTodos();
        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
    }

    @Test
    void listarTodos_deveRetornarListaVazia() {
        when(filmeRepository.findAll()).thenReturn(Collections.emptyList());
        List<FilmeDTO> result = filmeService.listarTodos();
        assertTrue(result.isEmpty());
    }

    @Test
    void buscarPorId_deveRetornarFilmeDTOQuandoEncontrado() {
        Filme filme = new Filme();
        filme.setId(1L);
        filme.setTitulo("Matrix");
        FilmeDTO dto = new FilmeDTO();
        dto.setId(1L);
        dto.setTitulo("Matrix");
        when(filmeRepository.findById(1L)).thenReturn(Optional.of(filme));
        when(filmeMapper.toDTO(filme)).thenReturn(dto);
        Optional<FilmeDTO> result = filmeService.buscarPorId(1L);
        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
    }

    @Test
    void buscarPorId_deveRetornarEmptyQuandoNaoEncontrado() {
        when(filmeRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<FilmeDTO> result = filmeService.buscarPorId(1L);
        assertFalse(result.isPresent());
    }

    @Test
    void criar_deveSalvarERetornarFilmeDTO() {
        FilmeCreateDTO createDTO = new FilmeCreateDTO();
        createDTO.setTitulo("Matrix");
        Filme filme = new Filme();
        filme.setId(1L);
        filme.setTitulo("Matrix");
        FilmeDTO dto = new FilmeDTO();
        dto.setId(1L);
        dto.setTitulo("Matrix");
        when(filmeMapper.fromCreateDTO(createDTO)).thenReturn(filme);
        when(filmeRepository.save(filme)).thenReturn(filme);
        when(filmeMapper.toDTO(filme)).thenReturn(dto);
        FilmeDTO result = filmeService.criar(createDTO);
        assertEquals(dto, result);
    }

    @Test
    void atualizar_deveAtualizarERetornarFilmeDTOQuandoEncontrado() {
        Filme filme = new Filme();
        filme.setId(1L);
        filme.setTitulo("Matrix");
        FilmeUpdateDTO updateDTO = new FilmeUpdateDTO();
        updateDTO.setTitulo("Matrix Reloaded");
        FilmeDTO dto = new FilmeDTO();
        dto.setId(1L);
        dto.setTitulo("Matrix Reloaded");
        when(filmeRepository.findById(1L)).thenReturn(Optional.of(filme));
        doAnswer(invocation -> {
            filme.setTitulo(updateDTO.getTitulo());
            return null;
        }).when(filmeMapper).updateEntity(filme, updateDTO);
        when(filmeRepository.save(filme)).thenReturn(filme);
        when(filmeMapper.toDTO(filme)).thenReturn(dto);
        Optional<FilmeDTO> result = filmeService.atualizar(1L, updateDTO);
        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
        assertEquals("Matrix Reloaded", filme.getTitulo());
    }

    @Test
    void atualizar_deveRetornarEmptyQuandoNaoEncontrado() {
        FilmeUpdateDTO updateDTO = new FilmeUpdateDTO();
        updateDTO.setTitulo("Matrix Reloaded");
        when(filmeRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<FilmeDTO> result = filmeService.atualizar(1L, updateDTO);
        assertFalse(result.isPresent());
    }

    @Test
    void deletar_deveRetornarTrueQuandoExcluido() {
        when(filmeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(filmeRepository).deleteById(1L);
        assertTrue(filmeService.deletar(1L));
        verify(filmeRepository).deleteById(1L);
    }

    @Test
    void deletar_deveRetornarFalseQuandoNaoExiste() {
        when(filmeRepository.existsById(1L)).thenReturn(false);
        assertFalse(filmeService.deletar(1L));
        verify(filmeRepository, never()).deleteById(anyLong());
    }

    @Test
    void buscarPorTitulo_deveRetornarListaDeFilmeDTO() {
        Filme filme = new Filme();
        filme.setId(1L);
        filme.setTitulo("Matrix");
        FilmeDTO dto = new FilmeDTO();
        dto.setId(1L);
        dto.setTitulo("Matrix");
        List<Filme> filmes = Collections.singletonList(filme);
        when(filmeRepository.findByTituloContainingIgnoreCase("Matrix")).thenReturn(filmes);
        when(filmeMapper.toDTO(filme)).thenReturn(dto);
        List<FilmeDTO> result = filmeService.buscarPorTitulo("Matrix");
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void buscarPorTitulo_deveRetornarListaVaziaQuandoNaoEncontrado() {
        when(filmeRepository.findByTituloContainingIgnoreCase("Matrix")).thenReturn(Collections.emptyList());
        List<FilmeDTO> result = filmeService.buscarPorTitulo("Matrix");
        assertTrue(result.isEmpty());
    }
}

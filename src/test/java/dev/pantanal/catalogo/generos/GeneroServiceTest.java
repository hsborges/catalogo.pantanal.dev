package dev.pantanal.catalogo.generos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GeneroServiceTest {

    @MockitoBean
    private GeneroRepository generoRepository;

    @MockitoBean
    private GeneroMapper generoMapper;

    @Autowired
    private GeneroService generoService;

    @Test
    void listarTodos_deveRetornarListaDeGeneroDTO() {
        Genero genero1 = new Genero(1L, "Ação");
        Genero genero2 = new Genero(2L, "Drama");

        List<Genero> generos = Arrays.asList(genero1, genero2);

        when(generoRepository.findAll()).thenReturn(generos);

        GeneroDTO dto1 = new GeneroDTO(1L, "Ação");
        GeneroDTO dto2 = new GeneroDTO(2L, "Drama");

        when(generoMapper.toDTO(genero1)).thenReturn(dto1);
        when(generoMapper.toDTO(genero2)).thenReturn(dto2);

        List<GeneroDTO> result = generoService.listarTodos();
        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
    }

    @Test
    void listarTodos_deveRetornarListaVazia() {
        when(generoRepository.findAll()).thenReturn(Collections.emptyList());
        List<GeneroDTO> result = generoService.listarTodos();
        assertTrue(result.isEmpty());
    }

    @Test
    void buscarPorId_deveRetornarGeneroDTOQuandoEncontrado() {
        Genero genero = new Genero(1L, "Ação");
        GeneroDTO dto = new GeneroDTO(1L, "Ação");
        when(generoRepository.findById(1L)).thenReturn(Optional.of(genero));
        when(generoMapper.toDTO(genero)).thenReturn(dto);
        Optional<GeneroDTO> result = generoService.buscarPorId(1L);
        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
    }

    @Test
    void buscarPorId_deveRetornarEmptyQuandoNaoEncontrado() {
        when(generoRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<GeneroDTO> result = generoService.buscarPorId(1L);
        assertFalse(result.isPresent());
    }

    @Test
    void buscarEntidadePorId_deveRetornarGeneroQuandoEncontrado() {
        Genero genero = new Genero(1L, "Ação");
        when(generoRepository.findById(1L)).thenReturn(Optional.of(genero));
        Optional<Genero> result = generoService.buscarEntidadePorId(1L);
        assertTrue(result.isPresent());
        assertEquals(genero, result.get());
    }

    @Test
    void buscarEntidadePorId_deveRetornarEmptyQuandoNaoEncontrado() {
        when(generoRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Genero> result = generoService.buscarEntidadePorId(1L);
        assertFalse(result.isPresent());
    }

    @Test
    void existsByNome_deveRetornarTrueQuandoExiste() {
        when(generoRepository.existsByNome("Ação")).thenReturn(true);
        assertTrue(generoService.existsByNome("Ação"));
    }

    @Test
    void existsByNome_deveRetornarFalseQuandoNaoExiste() {
        when(generoRepository.existsByNome("Comédia")).thenReturn(false);
        assertFalse(generoService.existsByNome("Comédia"));
    }

    @Test
    void criar_deveSalvarERetornarGeneroDTO() {
        GeneroDTO dto = new GeneroDTO(null, "Aventura");
        Genero genero = new Genero(null, "Aventura");
        Genero salvo = new Genero(1L, "Aventura");
        GeneroDTO dtoSalvo = new GeneroDTO(1L, "Aventura");

        when(generoMapper.toEntity(dto)).thenReturn(genero);
        when(generoRepository.save(genero)).thenReturn(salvo);
        when(generoMapper.toDTO(salvo)).thenReturn(dtoSalvo);

        GeneroDTO result = generoService.criar(dto);
        assertEquals(dtoSalvo, result);
    }

    @Test
    void atualizar_deveAtualizarERetornarGeneroDTOQuandoEncontrado() {
        Genero genero = new Genero(1L, "Ação");
        GeneroDTO dto = new GeneroDTO(1L, "Suspense");
        when(generoRepository.findById(1L)).thenReturn(Optional.of(genero));
        when(generoRepository.save(genero)).thenReturn(genero);
        when(generoMapper.toDTO(genero)).thenReturn(dto);

        Optional<GeneroDTO> result = generoService.atualizar(1L, dto);
        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
        assertEquals("Suspense", genero.getNome());
    }

    @Test
    void atualizar_deveRetornarEmptyQuandoNaoEncontrado() {
        GeneroDTO dto = new GeneroDTO(1L, "Suspense");
        when(generoRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<GeneroDTO> result = generoService.atualizar(1L, dto);
        assertFalse(result.isPresent());
    }

    @Test
    void deletar_deveRetornarTrueQuandoExcluido() {
        when(generoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(generoRepository).deleteById(1L);
        assertTrue(generoService.deletar(1L));
        verify(generoRepository).deleteById(1L);
    }

    @Test
    void deletar_deveRetornarFalseQuandoNaoExiste() {
        when(generoRepository.existsById(1L)).thenReturn(false);
        assertFalse(generoService.deletar(1L));
        verify(generoRepository, never()).deleteById(anyLong());
    }
}

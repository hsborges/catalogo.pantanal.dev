package dev.pantanal.catalogo.pessoas;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import dev.pantanal.catalogo.pessoas.dto.PessoaDTO;
import dev.pantanal.catalogo.pessoas.dto.PessoaCreateDTO;
import dev.pantanal.catalogo.pessoas.dto.PessoaUpdateDTO;

@SpringBootTest
class PessoaServiceTest {

    @MockitoBean
    private PessoaRepository pessoaRepository;

    @MockitoBean
    private PessoaMapper pessoaMapper;

    @Autowired
    private PessoaService pessoaService;

    @Test
    void criar_deveSalvarERetornarPessoaDTO() {
        PessoaCreateDTO createDTO = new PessoaCreateDTO();
        createDTO.setNome("Hudson");
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Hudson");
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(1L);
        pessoaDTO.setNome("Hudson");

        when(pessoaMapper.toEntity(createDTO)).thenReturn(pessoa);
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
        when(pessoaMapper.toDTO(pessoa)).thenReturn(pessoaDTO);

        PessoaDTO result = pessoaService.criar(createDTO);
        assertEquals(pessoaDTO, result);
    }

    @Test
    void listarTodos_deveRetornarPaginaDePessoaDTO() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Hudson");
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(1L);
        pessoaDTO.setNome("Hudson");
        Pageable pageable = PageRequest.of(0, 10);
        Page<Pessoa> pagePessoa = new PageImpl<>(Collections.singletonList(pessoa));

        when(pessoaRepository.findAll(pageable)).thenReturn(pagePessoa);
        when(pessoaMapper.toDTO(pessoa)).thenReturn(pessoaDTO);

        Page<PessoaDTO> result = pessoaService.listarTodos(pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals(pessoaDTO, result.getContent().get(0));
    }

    @Test
    void buscarPorNomeIgnoreCase_deveRetornarPaginaDePessoaDTO() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Hudson");
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(1L);
        pessoaDTO.setNome("Hudson");
        Pageable pageable = PageRequest.of(0, 10);
        Page<Pessoa> pagePessoa = new PageImpl<>(Collections.singletonList(pessoa));

        when(pessoaRepository.findByNomeContainingIgnoreCase("Hudson", pageable)).thenReturn(pagePessoa);
        when(pessoaMapper.toDTO(pessoa)).thenReturn(pessoaDTO);

        Page<PessoaDTO> result = pessoaService.buscarPorNomeIgnoreCase("Hudson", pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals(pessoaDTO, result.getContent().get(0));
    }

    @Test
    void buscarPorId_deveRetornarPessoaDTOQuandoEncontrado() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Hudson");
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(1L);
        pessoaDTO.setNome("Hudson");
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(pessoaMapper.toDTO(pessoa)).thenReturn(pessoaDTO);
        Optional<PessoaDTO> result = pessoaService.buscarPorId(1L);
        assertTrue(result.isPresent());
        assertEquals(pessoaDTO, result.get());
    }

    @Test
    void buscarPorId_deveRetornarEmptyQuandoNaoEncontrado() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<PessoaDTO> result = pessoaService.buscarPorId(1L);
        assertFalse(result.isPresent());
    }

    @Test
    void atualizar_deveAtualizarERetornarPessoaDTOQuandoEncontrado() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Hudson");
        PessoaUpdateDTO updateDTO = new PessoaUpdateDTO();
        updateDTO.setNome("Borges");
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(1L);
        pessoaDTO.setNome("Borges");
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        doAnswer(invocation -> {
            pessoa.setNome(updateDTO.getNome());
            return null;
        }).when(pessoaMapper).updateEntity(pessoa, updateDTO);
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
        when(pessoaMapper.toDTO(pessoa)).thenReturn(pessoaDTO);
        Optional<PessoaDTO> result = pessoaService.atualizar(1L, updateDTO);
        assertTrue(result.isPresent());
        assertEquals(pessoaDTO, result.get());
        assertEquals("Borges", pessoa.getNome());
    }

    @Test
    void atualizar_deveRetornarEmptyQuandoNaoEncontrado() {
        PessoaUpdateDTO updateDTO = new PessoaUpdateDTO();
        updateDTO.setNome("Borges");
        when(pessoaRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<PessoaDTO> result = pessoaService.atualizar(1L, updateDTO);
        assertFalse(result.isPresent());
    }

    @Test
    void deletar_deveRetornarTrueQuandoExcluido() {
        when(pessoaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(pessoaRepository).deleteById(1L);
        assertTrue(pessoaService.deletar(1L));
        verify(pessoaRepository).deleteById(1L);
    }

    @Test
    void deletar_deveRetornarFalseQuandoNaoExiste() {
        when(pessoaRepository.existsById(1L)).thenReturn(false);
        assertFalse(pessoaService.deletar(1L));
        verify(pessoaRepository, never()).deleteById(anyLong());
    }

    @Test
    void buscarPorNomeIgnoreCase_deveRetornarPessoaDTOQuandoEncontrado() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Hudson");
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(1L);
        pessoaDTO.setNome("Hudson");
        when(pessoaRepository.findByNome("Hudson")).thenReturn(pessoa);
        when(pessoaMapper.toDTO(pessoa)).thenReturn(pessoaDTO);
        PessoaDTO result = pessoaService.buscarPorNomeIgnoreCase("Hudson");
        assertEquals(pessoaDTO, result);
    }

    @Test
    void buscarPorNomeIgnoreCase_deveRetornarNullQuandoNaoEncontrado() {
        when(pessoaRepository.findByNome("Hudson")).thenReturn(null);
        PessoaDTO result = pessoaService.buscarPorNomeIgnoreCase("Hudson");
        assertNull(result);
    }
}

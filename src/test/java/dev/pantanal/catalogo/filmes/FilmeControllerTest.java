package dev.pantanal.catalogo.filmes;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.pantanal.catalogo.filmes.dto.FilmeDTO;
import dev.pantanal.catalogo.filmes.dto.FilmeCreateDTO;
import dev.pantanal.catalogo.filmes.dto.FilmeUpdateDTO;

@SpringBootTest
@AutoConfigureMockMvc
class FilmeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FilmeService filmeService;

    @Autowired
    private ObjectMapper objectMapper;

    private FilmeDTO filmeDTO;
    private FilmeCreateDTO filmeCreateDTO;
    private FilmeUpdateDTO filmeUpdateDTO;

    @BeforeEach
    void setUp() {
        filmeDTO = new FilmeDTO();
        filmeDTO.setId(1L);
        filmeDTO.setTitulo("Matrix");
        filmeCreateDTO = new FilmeCreateDTO();
        filmeCreateDTO.setTitulo("Matrix");
        filmeUpdateDTO = new FilmeUpdateDTO();
        filmeUpdateDTO.setTitulo("Matrix Reloaded");
    }

    @Nested
    class Listar {
        @Test
        void listarTodos_deveRetornar200ELista() throws Exception {
            when(filmeService.listarTodos()).thenReturn(Arrays.asList(filmeDTO));
            mockMvc.perform(get("/api/filmes"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(1L))
                    .andExpect(jsonPath("$[0].titulo").value("Matrix"));
        }

        @Test
        void listarTodos_deveRetornar200EListaVazia() throws Exception {
            when(filmeService.listarTodos()).thenReturn(Collections.emptyList());
            mockMvc.perform(get("/api/filmes"))
                    .andExpect(status().isOk())
                    .andExpect(content().json("[]"));
        }

        @Test
        void buscarPorTitulo_deveRetornar200EListaFiltrada() throws Exception {
            when(filmeService.buscarPorTitulo("Matrix")).thenReturn(Arrays.asList(filmeDTO));
            mockMvc.perform(get("/api/filmes?titulo=Matrix"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(1L))
                    .andExpect(jsonPath("$[0].titulo").value("Matrix"));
        }
    }

    @Nested
    class Detalhes {
        @Test
        void buscarPorId_deveRetornar200EFilmeDTO() throws Exception {
            when(filmeService.buscarPorId(1L)).thenReturn(Optional.of(filmeDTO));
            mockMvc.perform(get("/api/filmes/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.titulo").value("Matrix"));
        }

        @Test
        void buscarPorId_deveRetornar404QuandoNaoEncontrado() throws Exception {
            when(filmeService.buscarPorId(99L)).thenReturn(Optional.empty());
            mockMvc.perform(get("/api/filmes/99"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class Criar {
        @Test
        void criar_deveRetornar201EFilmeDTOQuandoValido() throws Exception {
            FilmeCreateDTO salvo = new FilmeCreateDTO();
            salvo.setTitulo("Matrix");
            salvo.setClassificacao("Ficção Científica");
            salvo.setElenco(List.of(1l));
            salvo.setDiretor(1l);
            salvo.setGeneros(List.of(1l));
            salvo.setDuracaoMinutos(100);
            salvo.setDistribuidora("null");
            salvo.setLancamento(LocalDate.now());
            when(filmeService.criar(any(FilmeCreateDTO.class))).thenReturn(filmeDTO);

            mockMvc.perform(post("/api/filmes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(salvo)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.titulo").value("Matrix"));
        }

        @Test
        void criar_deveRetornar400QuandoTituloNulo() throws Exception {
            FilmeCreateDTO dto = new FilmeCreateDTO();
            dto.setTitulo(null);
            mockMvc.perform(post("/api/filmes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void criar_deveRetornar400QuandoTituloVazio() throws Exception {
            FilmeCreateDTO dto = new FilmeCreateDTO();
            dto.setTitulo("");
            mockMvc.perform(post("/api/filmes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void criar_deveRetornar400QuandoTituloTemApenasEspacos() throws Exception {
            FilmeCreateDTO dto = new FilmeCreateDTO();
            dto.setTitulo("   ");
            mockMvc.perform(post("/api/filmes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class Atualizar {
        @Test
        void atualizar_deveRetornar200EFilmeDTOQuandoValido() throws Exception {
            FilmeDTO atualizado = new FilmeDTO();
            atualizado.setId(1L);
            atualizado.setTitulo("Matrix Reloaded");
            when(filmeService.atualizar(eq(1L), any(FilmeUpdateDTO.class))).thenReturn(Optional.of(atualizado));
            mockMvc.perform(put("/api/filmes/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(filmeUpdateDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.titulo").value("Matrix Reloaded"));
        }

        @Test
        void atualizar_deveRetornar404QuandoNaoEncontrado() throws Exception {
            when(filmeService.atualizar(eq(99L), any(FilmeUpdateDTO.class))).thenReturn(Optional.empty());
            mockMvc.perform(put("/api/filmes/99")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(filmeUpdateDTO)))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class Deletar {
        @Test
        void deletar_deveRetornar204QuandoExcluido() throws Exception {
            when(filmeService.deletar(1L)).thenReturn(true);
            mockMvc.perform(delete("/api/filmes/1"))
                    .andExpect(status().isNoContent());
        }

        @Test
        void deletar_deveRetornar404QuandoNaoExiste() throws Exception {
            when(filmeService.deletar(99L)).thenReturn(false);
            mockMvc.perform(delete("/api/filmes/99"))
                    .andExpect(status().isNotFound());
        }
    }
}

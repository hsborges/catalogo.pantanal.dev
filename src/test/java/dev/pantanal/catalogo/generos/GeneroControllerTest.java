package dev.pantanal.catalogo.generos;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

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

@SpringBootTest
@AutoConfigureMockMvc
class GeneroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GeneroService generoService;

    @Autowired
    private ObjectMapper objectMapper;

    private GeneroDTO generoDTO;

    @BeforeEach
    void setUp() {
        generoDTO = new GeneroDTO();
        generoDTO.setId(1L);
        generoDTO.setNome("Ação");
    }

    @Nested
    class Listar {
        @Test
        void listarTodos_deveRetornar200ELista() throws Exception {
            when(generoService.listarTodos()).thenReturn(Arrays.asList(generoDTO));
            mockMvc.perform(get("/api/generos"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(1L))
                    .andExpect(jsonPath("$[0].nome").value("Ação"));
        }

        @Test
        void listarTodos_deveRetornar200EListaVazia() throws Exception {
            when(generoService.listarTodos()).thenReturn(Collections.emptyList());
            mockMvc.perform(get("/api/generos"))
                    .andExpect(status().isOk())
                    .andExpect(content().json("[]"));
        }
    }

    @Nested
    class Detalhes {
        @Test
        void buscarPorId_deveRetornar200EGeneroDTO() throws Exception {
            when(generoService.buscarPorId(1L)).thenReturn(Optional.of(generoDTO));
            mockMvc.perform(get("/api/generos/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.nome").value("Ação"));
        }

        @Test
        void buscarPorId_deveRetornar404QuandoNaoEncontrado() throws Exception {
            when(generoService.buscarPorId(99L)).thenReturn(Optional.empty());
            mockMvc.perform(get("/api/generos/99"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class Criar {
        @Test
        void criar_deveRetornar400QuandoNomeNulo() throws Exception {
            GeneroDTO dto = new GeneroDTO();
            dto.setNome(null);
            mockMvc.perform(post("/api/generos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void criar_deveRetornar400QuandoNomeVazio() throws Exception {
            GeneroDTO dto = new GeneroDTO();
            dto.setNome("");
            mockMvc.perform(post("/api/generos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void criar_deveRetornar409QuandoNomeDuplicado() throws Exception {
            GeneroDTO dto = new GeneroDTO();
            dto.setNome("Ação");
            when(generoService.existsByNome("Ação")).thenReturn(true);
            mockMvc.perform(post("/api/generos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isConflict());
        }

        @Test
        void criar_deveRetornar201EGeneroDTOQuandoValido() throws Exception {
            GeneroDTO dto = new GeneroDTO();
            dto.setNome("Aventura");
            GeneroDTO salvo = new GeneroDTO();
            salvo.setId(2L);
            salvo.setNome("Aventura");
            when(generoService.existsByNome("Aventura")).thenReturn(false);
            when(generoService.criar(dto)).thenReturn(salvo);
            mockMvc.perform(post("/api/generos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(2L))
                    .andExpect(jsonPath("$.nome").value("Aventura"));
        }

        @Test
        void criar_deveRetornar400QuandoNomeTemApenasEspacos() throws Exception {
            GeneroDTO dto = new GeneroDTO();
            dto.setNome("   ");
            mockMvc.perform(post("/api/generos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void criar_deveRetornar201QuandoNomeNoLimiteInferior() throws Exception {
            GeneroDTO dto = new GeneroDTO();
            dto.setNome("A");
            when(generoService.existsByNome("A")).thenReturn(false);
            GeneroDTO salvo = new GeneroDTO();
            salvo.setId(4L);
            salvo.setNome("A");
            when(generoService.criar(dto)).thenReturn(salvo);
            mockMvc.perform(post("/api/generos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(4L))
                    .andExpect(jsonPath("$.nome").value("A"));
        }
    }

    @Nested
    class Atualizar {
        @Test
        void atualizar_deveRetornar200EGeneroDTOQuandoValido() throws Exception {
            GeneroDTO dto = new GeneroDTO();
            dto.setNome("Suspense");
            GeneroDTO atualizado = new GeneroDTO();
            atualizado.setId(1L);
            atualizado.setNome("Suspense");
            when(generoService.atualizar(eq(1L), any(GeneroDTO.class))).thenReturn(Optional.of(atualizado));
            mockMvc.perform(put("/api/generos/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.nome").value("Suspense"));
        }

        @Test
        void atualizar_deveRetornar404QuandoNaoEncontrado() throws Exception {
            GeneroDTO dto = new GeneroDTO();
            dto.setNome("Suspense");
            when(generoService.atualizar(eq(99L), any(GeneroDTO.class))).thenReturn(Optional.empty());
            mockMvc.perform(put("/api/generos/99")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class Deletar {
        @Test
        void deletar_deveRetornar204QuandoExcluido() throws Exception {
            when(generoService.deletar(1L)).thenReturn(true);
            mockMvc.perform(delete("/api/generos/1"))
                    .andExpect(status().isNoContent());
        }

        @Test
        void deletar_deveRetornar404QuandoNaoExiste() throws Exception {
            when(generoService.deletar(99L)).thenReturn(false);
            mockMvc.perform(delete("/api/generos/99"))
                    .andExpect(status().isNotFound());
        }
    }

}

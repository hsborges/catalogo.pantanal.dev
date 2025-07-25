package dev.pantanal.catalogo.pessoas;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dev.pantanal.catalogo.pessoas.dto.PessoaDTO;
import dev.pantanal.catalogo.pessoas.dto.PessoaCreateDTO;
import dev.pantanal.catalogo.pessoas.dto.PessoaUpdateDTO;

@SpringBootTest
@AutoConfigureMockMvc
class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PessoaService pessoaService;

    @Autowired
    private ObjectMapper objectMapper;

    private PessoaDTO pessoaDTO;
    private PessoaCreateDTO pessoaCreateDTO;
    private PessoaUpdateDTO pessoaUpdateDTO;

    @BeforeEach
    void setUp() {
        pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(1L);
        pessoaDTO.setNome("Hudson");
        pessoaDTO.setBiografia("Hudson é um desenvolvedor de software.");
        pessoaDTO.setDataNascimento(LocalDate.of(1990, 1, 1));

        pessoaCreateDTO = new PessoaCreateDTO();
        pessoaCreateDTO.setNome("Hudson");
        pessoaCreateDTO.setBiografia("Hudson é um desenvolvedor de software.");
        pessoaCreateDTO.setDataNascimento(LocalDate.of(1990, 1, 1));

        pessoaUpdateDTO = new PessoaUpdateDTO();
        pessoaUpdateDTO.setNome("Borges");
        pessoaUpdateDTO.setBiografia("Borges é um desenvolvedor de software.");
        pessoaUpdateDTO.setDataNascimento(LocalDate.of(1990, 1, 1));
    }

    @Nested
    class Listar {
        @Test
        void listarTodos_deveRetornar200EListaComHeaders() throws Exception {
            Pageable pageable = PageRequest.of(0, 10);
            Page<PessoaDTO> page = new PageImpl<>(Collections.singletonList(pessoaDTO), pageable, 1);
            when(pessoaService.listarTodos(pageable)).thenReturn(page);
            mockMvc.perform(get("/api/pessoas?page=0&size=10"))
                    .andExpect(status().isOk())
                    .andExpect(header().string("X-Total-Count", "1"))
                    .andExpect(header().string("X-Total-Pages", "1"))
                    .andExpect(header().string("X-Page-Number", "0"))
                    .andExpect(header().string("X-Page-Size", "10"))
                    .andExpect(jsonPath("$[0].id").value(1L))
                    .andExpect(jsonPath("$[0].nome").value("Hudson"));
        }

        @Test
        void listarTodos_deveRetornar200EListaVazia() throws Exception {
            Pageable pageable = PageRequest.of(0, 10);
            Page<PessoaDTO> page = new PageImpl<>(Collections.emptyList(), pageable, 0);
            when(pessoaService.listarTodos(pageable)).thenReturn(page);
            mockMvc.perform(get("/api/pessoas?page=0&size=10"))
                    .andExpect(status().isOk())
                    .andExpect(header().string("X-Total-Count", "0"))
                    .andExpect(header().string("X-Total-Pages", "0"))
                    .andExpect(header().string("X-Page-Number", "0"))
                    .andExpect(header().string("X-Page-Size", "10"))
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$").isEmpty());
        }

        @Test
        void listarTodos_filtraPorNome() throws Exception {
            Pageable pageable = PageRequest.of(0, 10);
            Page<PessoaDTO> page = new PageImpl<>(Collections.singletonList(pessoaDTO), pageable, 1);
            when(pessoaService.buscarPorNomeIgnoreCase(eq("Hudson"), eq(pageable))).thenReturn(page);
            mockMvc.perform(get("/api/pessoas?nome=Hudson&page=0&size=10"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(1L))
                    .andExpect(jsonPath("$[0].nome").value("Hudson"));
        }
    }

    @Nested
    class Detalhes {
        @Test
        void buscarPorId_deveRetornar200EPessoaDTO() throws Exception {
            when(pessoaService.buscarPorId(1L)).thenReturn(Optional.of(pessoaDTO));
            mockMvc.perform(get("/api/pessoas/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.nome").value("Hudson"));
        }

        @Test
        void buscarPorId_deveRetornar404QuandoNaoEncontrado() throws Exception {
            when(pessoaService.buscarPorId(99L)).thenReturn(Optional.empty());
            mockMvc.perform(get("/api/pessoas/99"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class Criar {
        @Test
        void criar_deveRetornar201EPessoaDTOQuandoValido() throws Exception {
            when(pessoaService.criar(any(PessoaCreateDTO.class))).thenReturn(pessoaDTO);
            mockMvc.perform(post("/api/pessoas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(pessoaCreateDTO)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(pessoaDTO.getId()))
                    .andExpect(jsonPath("$.nome").value(pessoaDTO.getNome()))
                    .andExpect(jsonPath("$.biografia").value(pessoaDTO.getBiografia()))
                    .andExpect(jsonPath("$.dataNascimento").value(pessoaDTO.getDataNascimento().toString()));
        }

        @Test
        void criar_deveRetornar400QuandoNomeNulo() throws Exception {
            mockMvc.perform(post("/api/pessoas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(new PessoaCreateDTO())))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.messages.length()").value(3));
        }

        @Test
        void criar_deveRetornar400QuandoNomeTemApenasEspacos() throws Exception {
            PessoaCreateDTO dto = new PessoaCreateDTO();
            dto.setNome("   ");

            mockMvc.perform(post("/api/pessoas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.messages.length()").value(3));
        }

        @Test
        void criar_deveRetornar400QuandoBiografiaTemApenasEspacos() throws Exception {
            PessoaCreateDTO dto = new PessoaCreateDTO();
            dto.setBiografia("   ");

            mockMvc.perform(post("/api/pessoas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.messages.length()").value(3));
        }
    }

    @Nested
    class Atualizar {
        @Test
        void atualizar_deveRetornar200EPessoaDTOQuandoValido() throws Exception {
            PessoaDTO atualizado = new PessoaDTO();
            atualizado.setId(1L);
            atualizado.setNome("Borges");
            when(pessoaService.atualizar(eq(1L), any(PessoaUpdateDTO.class))).thenReturn(Optional.of(atualizado));
            mockMvc.perform(put("/api/pessoas/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(pessoaUpdateDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.nome").value("Borges"));
        }

        @Test
        void atualizar_deveRetornar404QuandoNaoEncontrado() throws Exception {
            when(pessoaService.atualizar(eq(99L), any(PessoaUpdateDTO.class))).thenReturn(Optional.empty());
            mockMvc.perform(put("/api/pessoas/99")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(pessoaUpdateDTO)))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class Deletar {
        @Test
        void deletar_deveRetornar204QuandoExcluido() throws Exception {
            when(pessoaService.deletar(1L)).thenReturn(true);
            mockMvc.perform(delete("/api/pessoas/1"))
                    .andExpect(status().isNoContent());
        }

        @Test
        void deletar_deveRetornar404QuandoNaoExiste() throws Exception {
            when(pessoaService.deletar(99L)).thenReturn(false);
            mockMvc.perform(delete("/api/pessoas/99"))
                    .andExpect(status().isNotFound());
        }
    }

}

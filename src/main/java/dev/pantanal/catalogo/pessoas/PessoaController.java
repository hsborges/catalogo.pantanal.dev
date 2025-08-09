package dev.pantanal.catalogo.pessoas;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.pantanal.catalogo.pessoas.dto.PessoaDTO;
import dev.pantanal.catalogo.pessoas.dto.PessoaListParamsDTO;
import dev.pantanal.catalogo.pessoas.dto.PessoaCreateDTO;
import dev.pantanal.catalogo.pessoas.dto.PessoaUpdateDTO;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/pessoas")
@Tag(name = "Pessoas", description = "CRUD de pessoas (atores, diretores, etc.)")
public class PessoaController {
    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping
    @Operation(summary = "Listar pessoas", description = "Lista pessoas com paginação e filtro por nome (case-insensitive). Retorna metadados em cabeçalhos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada", headers = {
                    @Header(name = "X-Total-Count", description = "Total de registros"),
                    @Header(name = "X-Total-Pages", description = "Total de páginas"),
                    @Header(name = "X-Page-Number", description = "Página atual (0-based)"),
                    @Header(name = "X-Page-Size", description = "Tamanho da página")
            }, content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PessoaDTO.class))))
    })
    public ResponseEntity<Iterable<PessoaDTO>> listarTodos(
            @Valid @ModelAttribute PessoaListParamsDTO params) {
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize());
        Page<PessoaDTO> resultPage;
        if (params.getNome() != null && !params.getNome().isBlank()) {
            resultPage = pessoaService.buscarPorNomeIgnoreCase(params.getNome(), pageable);
        } else {
            resultPage = pessoaService.listarTodos(pageable);
        }

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(resultPage.getTotalElements()))
                .header("X-Total-Pages", String.valueOf(resultPage.getTotalPages()))
                .header("X-Page-Number", String.valueOf(resultPage.getNumber()))
                .header("X-Page-Size", String.valueOf(resultPage.getSize()))
                .body(resultPage.getContent());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalhar pessoa", description = "Busca uma pessoa pelo identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PessoaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Não encontrado", content = @Content)
    })
    public ResponseEntity<PessoaDTO> buscarPorId(
            @PathVariable @Parameter(description = "Identificador da pessoa", example = "1") Long id) {
        return pessoaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar pessoa", description = "Cria uma nova pessoa")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PessoaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    })
    public ResponseEntity<PessoaDTO> criar(
            @Valid @RequestBody PessoaCreateDTO dto) {
        PessoaDTO criado = pessoaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pessoa", description = "Atualiza uma pessoa existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atualizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PessoaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Não encontrado", content = @Content)
    })
    public ResponseEntity<PessoaDTO> atualizar(
            @PathVariable @Parameter(description = "Identificador da pessoa", example = "1") Long id,
            @Valid @RequestBody PessoaUpdateDTO dto) {
        return pessoaService.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar pessoa", description = "Remove uma pessoa pelo identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Removido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Não encontrado", content = @Content)
    })
    public ResponseEntity<Void> deletar(
            @PathVariable @Parameter(description = "Identificador da pessoa", example = "1") Long id) {
        if (pessoaService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

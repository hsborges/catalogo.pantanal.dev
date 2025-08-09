package dev.pantanal.catalogo.generos;

import java.util.List;

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

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/generos")
@Tag(name = "Gêneros", description = "CRUD de gêneros cinematográficos")
public class GeneroController {
    private final GeneroService generoService;

    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    @GetMapping
    @Operation(summary = "Listar gêneros", description = "Lista todos os gêneros cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GeneroDTO.class))))
    })
    public ResponseEntity<List<GeneroDTO>> listarTodos() {
        List<GeneroDTO> dtos = generoService.listarTodos();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalhar gênero", description = "Busca um gênero pelo identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneroDTO.class))),
            @ApiResponse(responseCode = "404", description = "Não encontrado", content = @Content)
    })
    public ResponseEntity<GeneroDTO> buscarPorId(
            @PathVariable @Parameter(description = "Identificador do gênero", example = "1") Long id) {
        return generoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar gênero", description = "Cria um novo gênero cinematográfico")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneroDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflito (nome já existe)", content = @Content)
    })
    public ResponseEntity<GeneroDTO> criar(
            @Valid @RequestBody GeneroDTO dto) {
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        if (generoService.existsByNome(dto.getNome())) {
            return ResponseEntity.status(409).build();
        }
        GeneroDTO salvo = generoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar gênero", description = "Atualiza um gênero existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atualizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneroDTO.class))),
            @ApiResponse(responseCode = "404", description = "Não encontrado", content = @Content)
    })
    public ResponseEntity<GeneroDTO> atualizar(
            @PathVariable @Parameter(description = "Identificador do gênero", example = "1") Long id,
            @RequestBody GeneroDTO dto) {
        return generoService.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar gênero", description = "Remove um gênero pelo identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Removido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Não encontrado", content = @Content)
    })
    public ResponseEntity<Void> deletar(
            @PathVariable @Parameter(description = "Identificador do gênero", example = "1") Long id) {
        if (!generoService.deletar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

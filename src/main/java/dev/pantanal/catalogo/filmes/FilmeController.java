package dev.pantanal.catalogo.filmes;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.pantanal.catalogo.filmes.dto.FilmeDTO;
import dev.pantanal.catalogo.filmes.dto.FilmeCreateDTO;
import dev.pantanal.catalogo.filmes.dto.FilmeUpdateDTO;
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
@RequestMapping("/api/filmes")
@Tag(name = "Filmes", description = "CRUD de filmes e busca por título")
public class FilmeController {
    private final FilmeService filmeService;

    public FilmeController(FilmeService filmeService) {
        this.filmeService = filmeService;
    }

    @GetMapping
    @Operation(summary = "Listar filmes", description = "Lista todos os filmes ou filtra por título (contendo).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FilmeDTO.class))))
    })
    public ResponseEntity<List<FilmeDTO>> listarTodos(
            @RequestParam(value = "titulo", required = false) @Parameter(name = "titulo", description = "Filtro de título (contém)", example = "Matrix") String titulo) {
        if (titulo != null && !titulo.isBlank()) {
            return ResponseEntity.ok(filmeService.buscarPorTitulo(titulo));
        }
        return ResponseEntity.ok(filmeService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalhar filme", description = "Busca um filme pelo identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FilmeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Não encontrado", content = @Content)
    })
    public ResponseEntity<FilmeDTO> buscarPorId(
            @PathVariable @Parameter(description = "Identificador do filme", example = "1") Long id) {
        return filmeService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar filme", description = "Cria um novo filme")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FilmeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    })
    public ResponseEntity<FilmeDTO> criar(
            @Valid @RequestBody FilmeCreateDTO dto) {
        FilmeDTO criado = filmeService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar filme", description = "Atualiza um filme existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atualizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FilmeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Não encontrado", content = @Content)
    })
    public ResponseEntity<FilmeDTO> atualizar(
            @PathVariable @Parameter(description = "Identificador do filme", example = "1") Long id,
            @Valid @RequestBody FilmeUpdateDTO dto) {
        return filmeService.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar filme", description = "Remove um filme pelo identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Removido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Não encontrado", content = @Content)
    })
    public ResponseEntity<Void> deletar(
            @PathVariable @Parameter(description = "Identificador do filme", example = "1") Long id) {
        if (filmeService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

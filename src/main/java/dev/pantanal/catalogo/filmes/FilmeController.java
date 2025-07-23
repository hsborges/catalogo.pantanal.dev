package dev.pantanal.catalogo.filmes;

import java.util.List;

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

@RestController
@RequestMapping("/api/filmes")
public class FilmeController {
    private final FilmeService filmeService;

    public FilmeController(FilmeService filmeService) {
        this.filmeService = filmeService;
    }

    @GetMapping
    public ResponseEntity<List<FilmeDTO>> listarTodos(@RequestParam(value = "titulo", required = false) String titulo) {
        if (titulo != null && !titulo.isBlank()) {
            return ResponseEntity.ok(filmeService.buscarPorTitulo(titulo));
        }
        return ResponseEntity.ok(filmeService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmeDTO> buscarPorId(@PathVariable Long id) {
        return filmeService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FilmeDTO> criar(@Valid @RequestBody FilmeCreateDTO dto) {
        FilmeDTO criado = filmeService.criar(dto);
        return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmeDTO> atualizar(@PathVariable Long id, @Valid @RequestBody FilmeUpdateDTO dto) {
        return filmeService.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (filmeService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

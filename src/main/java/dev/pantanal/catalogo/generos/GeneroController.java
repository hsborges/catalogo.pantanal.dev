package dev.pantanal.catalogo.generos;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/generos")
@RequiredArgsConstructor
public class GeneroController {
    private final GeneroRepository generoRepository;

    @GetMapping
    public ResponseEntity<List<GeneroDTO>> listarTodos() {
        List<GeneroDTO> dtos = generoRepository.findAll().stream()
                .map(GeneroMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneroDTO> buscarPorId(@PathVariable Long id) {
        return generoRepository.findById(id)
                .map(GeneroMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GeneroDTO> criar(@RequestBody GeneroDTO dto) {
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        if (generoRepository.existsByNome(dto.getNome())) {
            return ResponseEntity.status(409).build();
        }
        Genero genero = GeneroMapper.toEntity(dto);
        Genero salvo = generoRepository.save(genero);
        return ResponseEntity.ok(GeneroMapper.toDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneroDTO> atualizar(@PathVariable Long id, @RequestBody GeneroDTO dto) {
        return generoRepository.findById(id)
                .map(genero -> {
                    genero.setNome(dto.getNome());
                    generoRepository.save(genero);
                    return ResponseEntity.ok(GeneroMapper.toDTO(genero));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!generoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        generoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

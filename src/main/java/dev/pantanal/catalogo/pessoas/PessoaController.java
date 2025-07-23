package dev.pantanal.catalogo.pessoas;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
@RequestMapping("/api/pessoas")
public class PessoaController {
    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listarTodos(@RequestParam(value = "nome", required = false) String nome) {
        if (nome != null && !nome.isBlank()) {
            PessoaDTO pessoa = pessoaService.buscarPorNomeIgnoreCase(nome);
            if (pessoa != null) {
                return ResponseEntity.ok(List.of(pessoa));
            } else {
                return ResponseEntity.ok(List.of());
            }
        }
        return ResponseEntity.ok(pessoaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> buscarPorId(@PathVariable Long id) {
        return pessoaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PessoaDTO> criar(@RequestBody PessoaDTO dto) {
        PessoaDTO criado = pessoaService.criar(dto);
        return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> atualizar(@PathVariable Long id, @RequestBody PessoaDTO dto) {
        return pessoaService.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (pessoaService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

package dev.pantanal.catalogo.pessoas;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Pessoa findByNome(String nome);

    Page<Pessoa> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}

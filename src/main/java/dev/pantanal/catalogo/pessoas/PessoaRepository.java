package dev.pantanal.catalogo.pessoas;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Pessoa findByNome(String nome);
}

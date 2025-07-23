package dev.pantanal.catalogo.generos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneroRepository extends JpaRepository<Genero, Long> {
    boolean existsByNome(String nome);

    Genero findByNome(String nome);
}

package dev.pantanal.catalogo.filmes;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
    List<Filme> findByTituloContainingIgnoreCase(String titulo);
}

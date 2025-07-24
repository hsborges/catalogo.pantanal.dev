package dev.pantanal.catalogo.filmes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FilmeRepositoryTest {

    @Autowired
    private FilmeRepository filmeRepository;

    @Test
    void findByTituloContainingIgnoreCase_retornaFilmesComTituloParcial() {
        Filme f1 = new Filme();
        f1.setTitulo("O Senhor dos Anéis");
        filmeRepository.save(f1);
        Filme f2 = new Filme();
        f2.setTitulo("Senhor das Moscas");
        filmeRepository.save(f2);
        Filme f3 = new Filme();
        f3.setTitulo("Matrix");
        filmeRepository.save(f3);

        List<Filme> encontrados = filmeRepository.findByTituloContainingIgnoreCase("senhor");
        assertEquals(2, encontrados.size());
        assertTrue(encontrados.stream().anyMatch(f -> f.getTitulo().equals("O Senhor dos Anéis")));
        assertTrue(encontrados.stream().anyMatch(f -> f.getTitulo().equals("Senhor das Moscas")));
    }

    @Test
    void findByTituloContainingIgnoreCase_retornaVazioSeNaoEncontrar() {
        Filme f1 = new Filme();
        f1.setTitulo("Interestelar");
        filmeRepository.save(f1);
        List<Filme> encontrados = filmeRepository.findByTituloContainingIgnoreCase("avatar");
        assertTrue(encontrados.isEmpty());
    }
}

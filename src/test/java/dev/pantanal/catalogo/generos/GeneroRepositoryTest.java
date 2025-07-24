package dev.pantanal.catalogo.generos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GeneroRepositoryTest {

    @Autowired
    private GeneroRepository generoRepository;

    @Test
    void existsByNome_retornaTrueSeNomeExistir() {
        Genero genero = new Genero();
        genero.setNome("Ação");
        generoRepository.save(genero);
        assertTrue(generoRepository.existsByNome("Ação"));
    }

    @Test
    void existsByNome_retornaFalseSeNomeNaoExistir() {
        assertFalse(generoRepository.existsByNome("Comédia"));
    }

    @Test
    void findByNome_retornaGeneroSeExistir() {
        Genero genero = new Genero();
        genero.setNome("Drama");
        generoRepository.save(genero);
        Genero encontrado = generoRepository.findByNome("Drama");
        assertNotNull(encontrado);
        assertEquals("Drama", encontrado.getNome());
    }

    @Test
    void findByNome_retornaNullSeNaoExistir() {
        Genero encontrado = generoRepository.findByNome("Terror");
        assertNull(encontrado);
    }
}

package dev.pantanal.catalogo.filmes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import dev.pantanal.catalogo.generos.Genero;
import dev.pantanal.catalogo.pessoas.Pessoa;

class FilmeMapperTest {

    @Test
    void idsToGeneros_deveRetornarNullParaListaNula() {
        List<Genero> generos = FilmeMapper.idsToGeneros(null);
        assertNull(generos);
    }

    @Test
    void idsToGeneros_deveRetornarListaVaziaParaListaVazia() {
        List<Genero> generos = FilmeMapper.idsToGeneros(Arrays.asList());
        assertNotNull(generos);
        assertTrue(generos.isEmpty());
    }

    @Test
    void idToPessoa_deveRetornarNullParaIdNulo() {
        Pessoa pessoa = FilmeMapper.idToPessoa(null);
        assertNull(pessoa);
    }

    @Test
    void idsToPessoas_deveRetornarNullParaListaNula() {
        List<Pessoa> pessoas = FilmeMapper.idsToPessoas(null);
        assertNull(pessoas);
    }

    @Test
    void idsToPessoas_deveRetornarListaVaziaParaListaVazia() {
        List<Pessoa> pessoas = FilmeMapper.idsToPessoas(Arrays.asList());
        assertNotNull(pessoas);
        assertTrue(pessoas.isEmpty());
    }

    @Test
    void idsToGeneros_deveConverterIdsEmGeneros() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<Genero> generos = FilmeMapper.idsToGeneros(ids);
        assertEquals(2, generos.size());
        assertEquals(1L, generos.get(0).getId());
        assertEquals(2L, generos.get(1).getId());
    }

    @Test
    void idToPessoa_deveConverterIdEmPessoa() {
        Pessoa pessoa = FilmeMapper.idToPessoa(10L);
        assertNotNull(pessoa);
        assertEquals(10L, pessoa.getId());
    }

    @Test
    void idsToPessoas_deveConverterIdsEmPessoas() {
        List<Long> ids = Arrays.asList(5L, 6L);
        List<Pessoa> pessoas = FilmeMapper.idsToPessoas(ids);
        assertEquals(2, pessoas.size());
        assertEquals(5L, pessoas.get(0).getId());
        assertEquals(6L, pessoas.get(1).getId());
    }
}

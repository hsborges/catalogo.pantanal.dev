package dev.pantanal.catalogo.pessoas;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PessoaRepositoryTest {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Test
    void findByNome_retornaPessoaSeExistir() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Carlos");
        pessoaRepository.save(pessoa);
        Pessoa encontrada = pessoaRepository.findByNome("Carlos");
        assertNotNull(encontrada);
        assertEquals("Carlos", encontrada.getNome());
    }

    @Test
    void findByNome_retornaNullSeNaoExistir() {
        Pessoa encontrada = pessoaRepository.findByNome("Ana");
        assertNull(encontrada);
    }

    @Test
    void findByNomeContainingIgnoreCase_retornaPessoasComNomeParcial() {
        Pessoa p1 = new Pessoa();
        p1.setNome("Ana Paula");
        pessoaRepository.save(p1);
        Pessoa p2 = new Pessoa();
        p2.setNome("Paulo Henrique");
        pessoaRepository.save(p2);
        Pessoa p3 = new Pessoa();
        p3.setNome("Mariana");
        pessoaRepository.save(p3);

        var page = pessoaRepository.findByNomeContainingIgnoreCase("paul",
                PageRequest.of(0, 10));

        assertEquals(2, page.getTotalElements());
        assertTrue(page.getContent().stream().anyMatch(p -> p.getNome().equals("Ana Paula")));
        assertTrue(page.getContent().stream().anyMatch(p -> p.getNome().equals("Paulo Henrique")));
    }

    @Test
    void findByNomeContainingIgnoreCase_retornaVazioSeNaoEncontrar() {
        Pessoa p1 = new Pessoa();
        p1.setNome("Carlos");
        pessoaRepository.save(p1);
        var page = pessoaRepository.findByNomeContainingIgnoreCase("xyz",
                org.springframework.data.domain.PageRequest.of(0, 10));
        assertEquals(0, page.getTotalElements());
    }
}

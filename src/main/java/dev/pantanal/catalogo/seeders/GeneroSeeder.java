package dev.pantanal.catalogo.seeders;

import java.util.Arrays;
import java.util.List;

import dev.pantanal.catalogo.generos.Genero;
import dev.pantanal.catalogo.generos.GeneroRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class GeneroSeeder implements CommandLineRunner {
    private final GeneroRepository generoRepository;

    public GeneroSeeder(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    @Override
    public void run(String... args) {
        if (generoRepository.count() == 0) {
            List<String> nomes = Arrays.asList("Ação", "Aventura", "Ficção Científica", "Drama", "Comédia");
            for (String nome : nomes) {
                if (!generoRepository.existsByNome(nome)) {
                    generoRepository.save(Genero.builder().nome(nome).build());
                }
            }
        }
    }
}

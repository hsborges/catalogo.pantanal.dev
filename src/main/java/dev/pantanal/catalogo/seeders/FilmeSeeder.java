package dev.pantanal.catalogo.seeders;

import java.time.LocalDate;
import java.util.Arrays;

import dev.pantanal.catalogo.filmes.Filme;
import dev.pantanal.catalogo.filmes.FilmeRepository;
import dev.pantanal.catalogo.generos.Genero;
import dev.pantanal.catalogo.generos.GeneroRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class FilmeSeeder implements CommandLineRunner {
    private final FilmeRepository filmeRepository;
    private final GeneroRepository generoRepository;

    public FilmeSeeder(FilmeRepository filmeRepository, GeneroRepository generoRepository) {
        this.filmeRepository = filmeRepository;
        this.generoRepository = generoRepository;
    }

    @Override
    public void run(String... args) {
        if (filmeRepository.count() == 0) {
            // Busca os gêneros já persistidos
            java.util.function.Function<String, Genero> getGenero = nome -> generoRepository.findByNome(nome);

            Filme superman = Filme.builder()
                    .titulo("Superman")
                    .diretor("James Gunn")
                    .lancamento(LocalDate.of(2025, 7, 10))
                    .generos(Arrays.asList(
                            getGenero.apply("Ação"),
                            getGenero.apply("Aventura"),
                            getGenero.apply("Ficção Científica")))
                    .classificacao(14)
                    .duracaoMinutos(129)
                    .elenco(Arrays.asList(
                            "David Corenswet", "Rachel Brosnahan", "Nicholas Hoult", "Nathan Fillion",
                            "Isabela Merced"))
                    .distribuidora("Warner Bros.")
                    .capaUrl(
                            "https://m.media-amazon.com/images/M/MV5BZTk2YjUxODgtMTgxYS00NmQyLTk0OTctNmNhYjZjNTc0NDg1XkEyXkFqcGc@._V1_QL75_UY562_CR1,0,380,562_.jpg")
                    .trailerUrl("https://www.imdb.com/pt/video/vi311543833/")
                    .build();

            Filme quarteto = Filme.builder()
                    .titulo("Quarteto Fantástico: Primeiros Passos")
                    .diretor("Matt Shakman")
                    .lancamento(LocalDate.of(2025, 7, 24))
                    .generos(Arrays.asList(
                            getGenero.apply("Ação"),
                            getGenero.apply("Aventura"),
                            getGenero.apply("Ficção Científica")))
                    .classificacao(12)
                    .duracaoMinutos(115)
                    .elenco(Arrays.asList(
                            "Pedro Pascal", "Vanessa Kirby", "Ebon Moss-Bachrach", "Joseph Quinn", "Ralph Ineson"))
                    .distribuidora("Marvel Studios")
                    .capaUrl(
                            "https://m.media-amazon.com/images/M/MV5BYmY3NGMxNjgtMTM1My00OGZhLTg0NzMtNDY1YzFiNTcxNDA5XkEyXkFqcGc@._V1_QL75_UX380_CR0,0,380,562_.jpg")
                    .trailerUrl("https://www.imdb.com/pt/video/vi3218196505/")
                    .build();

            filmeRepository.save(superman);
            filmeRepository.save(quarteto);
        }
    }
}

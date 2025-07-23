package dev.pantanal.catalogo.seeders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import dev.pantanal.catalogo.pessoas.Pessoa;
import dev.pantanal.catalogo.pessoas.PessoaRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class PessoaSeeder implements CommandLineRunner {
    private final PessoaRepository pessoaRepository;

    public PessoaSeeder(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public void run(String... args) {
        if (pessoaRepository.count() == 0) {
            List<Pessoa> pessoas = Arrays.asList(
                    Pessoa.builder()
                            .nome("James Gunn")
                            .dataNascimento(LocalDate.of(1966, 8, 5))
                            .biografia(
                                    "James Gunn é um cineasta, roteirista e produtor americano, conhecido por dirigir Guardiões da Galáxia e O Esquadrão Suicida.")
                            .fotoUrl(
                                    "https://m.media-amazon.com/images/M/MV5BMTYxMDgzMjA5OV5BMl5BanBnXkFtZTcwMzMwMTUxNw@@._V1_QL75_UY414_CR15,0,280,414_.jpg")
                            .build(),
                    Pessoa.builder()
                            .nome("David Corenswet")
                            .dataNascimento(LocalDate.of(1993, 7, 8))
                            .biografia(
                                    "David Corenswet é um ator americano, conhecido por The Politician, Hollywood e Superman (2025).")
                            .fotoUrl(
                                    "https://m.media-amazon.com/images/M/MV5BMTYwYjQwYjUtYjQwZi00YjQwLTk0YjctYjQwYjQwYjQwYjQwXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_UY317_CR12,0,214,317_AL_.jpg")
                            .build(),
                    Pessoa.builder()
                            .nome("Rachel Brosnahan")
                            .dataNascimento(LocalDate.of(1990, 7, 12))
                            .biografia(
                                    "Rachel Brosnahan é uma atriz americana, vencedora do Emmy, conhecida por The Marvelous Mrs. Maisel e House of Cards.")
                            .fotoUrl(
                                    "https://m.media-amazon.com/images/M/MV5BZDAwNmJmYjctMDk0YS00NGI1LWJlOGEtNGZhODgyNjFjN2ZjXkEyXkFqcGc@._V1_QL75_UX280_CR0,3,280,414_.jpg")
                            .build(),
                    Pessoa.builder()
                            .nome("Nicholas Hoult")
                            .dataNascimento(LocalDate.of(1989, 12, 7))
                            .biografia(
                                    "Nicholas Hoult é um ator britânico, conhecido por X-Men, Mad Max: Estrada da Fúria e The Great.")
                            .fotoUrl(
                                    "https://m.media-amazon.com/images/M/MV5BZDE2YjY4ODUtZjVmMy00ZmE2LTgwZjgtMWJiZGI0NWY3ODAzXkEyXkFqcGc@._V1_QL75_UX280_CR0,3,280,414_.jpg")
                            .build(),
                    Pessoa.builder()
                            .nome("Nathan Fillion")
                            .dataNascimento(LocalDate.of(1971, 3, 27))
                            .biografia(
                                    "Nathan Fillion é um ator canadense, conhecido por Castle, Firefly e The Rookie.")
                            .fotoUrl(
                                    "https://m.media-amazon.com/images/M/MV5BMTkxODM3ODY2Nl5BMl5BanBnXkFtZTgwMzAyMjU2NzE@._V1_QL75_UY414_CR26,0,280,414_.jpg")
                            .build(),
                    Pessoa.builder()
                            .nome("Isabela Merced")
                            .dataNascimento(LocalDate.of(2001, 7, 10))
                            .biografia(
                                    "Isabela Merced é uma atriz e cantora americana, conhecida por Dora e a Cidade Perdida e Transformers: O Último Cavaleiro.")
                            .fotoUrl(
                                    "https://m.media-amazon.com/images/M/MV5BMjA2NjQwNjYwNl5BMl5BanBnXkFtZTgwODg2ODg2OTE@._V1_UY317_CR12,0,214,317_AL_.jpg")
                            .build(),
                    Pessoa.builder()
                            .nome("Matt Shakman")
                            .dataNascimento(LocalDate.of(1975, 8, 8))
                            .biografia(
                                    "Matt Shakman é um diretor e produtor americano, conhecido por WandaVision e Game of Thrones.")
                            .fotoUrl(
                                    "https://m.media-amazon.com/images/M/MV5BMjA2NjQwNjYwNl5BMl5BanBnXkFtZTgwODg2ODg2OTE@._V1_UY317_CR12,0,214,317_AL_.jpg")
                            .build(),
                    Pessoa.builder()
                            .nome("Pedro Pascal")
                            .dataNascimento(LocalDate.of(1975, 4, 2))
                            .biografia(
                                    "Pedro Pascal é um ator chileno-americano, conhecido por The Mandalorian, Game of Thrones e Narcos.")
                            .fotoUrl(
                                    "https://m.media-amazon.com/images/M/MV5BMjA2NjQwNjYwNl5BMl5BanBnXkFtZTgwODg2ODg2OTE@._V1_UY317_CR12,0,214,317_AL_.jpg")
                            .build(),
                    Pessoa.builder()
                            .nome("Vanessa Kirby")
                            .dataNascimento(LocalDate.of(1988, 4, 18))
                            .biografia(
                                    "Vanessa Kirby é uma atriz britânica, conhecida por The Crown, Missão: Impossível e Pieces of a Woman.")
                            .fotoUrl(
                                    "https://m.media-amazon.com/images/M/MV5BMjA2NjQwNjYwNl5BMl5BanBnXkFtZTgwODg2ODg2OTE@._V1_UY317_CR12,0,214,317_AL_.jpg")
                            .build(),
                    Pessoa.builder()
                            .nome("Ebon Moss-Bachrach")
                            .dataNascimento(LocalDate.of(1977, 3, 19))
                            .biografia("Ebon Moss-Bachrach é um ator americano, conhecido por The Bear, Girls e Andor.")
                            .fotoUrl(
                                    "https://m.media-amazon.com/images/M/MV5BZGQ2NzYzMzMtYjQxNy00MDI1LTkxODMtMDhkNjg0YjQ5MzIxXkEyXkFqcGc@._V1_QL75_UY414_CR171,0,280,414_.jpg")
                            .build(),
                    Pessoa.builder()
                            .nome("Joseph Quinn")
                            .dataNascimento(LocalDate.of(1994, 1, 26))
                            .biografia(
                                    "Joseph Quinn é um ator britânico, conhecido por Stranger Things e Game of Thrones.")
                            .fotoUrl(
                                    "https://m.media-amazon.com/images/M/MV5BMjA2NjQwNjYwNl5BMl5BanBnXkFtZTgwODg2ODg2OTE@._V1_UY317_CR12,0,214,317_AL_.jpg")
                            .build(),
                    Pessoa.builder()
                            .nome("Ralph Ineson")
                            .dataNascimento(LocalDate.of(1969, 12, 15))
                            .biografia(
                                    "Ralph Ineson é um ator britânico, conhecido por A Bruxa, Game of Thrones e Harry Potter.")
                            .fotoUrl(
                                    "https://m.media-amazon.com/images/M/MV5BMjA2NjQwNjYwNl5BMl5BanBnXkFtZTgwODg2ODg2OTE@._V1_UY317_CR12,0,214,317_AL_.jpg")
                            .build());
            pessoaRepository.saveAll(pessoas);
        }
    }
}

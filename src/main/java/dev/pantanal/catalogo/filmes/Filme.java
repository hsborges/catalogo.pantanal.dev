package dev.pantanal.catalogo.filmes;

import java.time.LocalDate;
import java.util.List;

import dev.pantanal.catalogo.generos.Genero;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String diretor;

    private LocalDate lancamento;

    @ManyToMany
    private List<Genero> generos;

    private Integer classificacao;

    private Integer duracaoMinutos;

    private List<String> elenco;

    private String distribuidora;

    private String capaUrl;

    private String trailerUrl;
}

package dev.pantanal.catalogo.filmes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.ElementCollection;

import lombok.Builder;

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

    private String genero;

    private Integer classificacao;

    private Integer duracaoMinutos;

    @ElementCollection
    private List<String> elenco;

    private String distribuidora;

    private String capaUrl;

    private String trailerUrl;
}

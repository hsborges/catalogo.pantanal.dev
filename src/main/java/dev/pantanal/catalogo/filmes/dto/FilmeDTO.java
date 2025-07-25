package dev.pantanal.catalogo.filmes.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import dev.pantanal.catalogo.generos.Genero;

import dev.pantanal.catalogo.pessoas.dto.PessoaDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmeDTO {
    private Long id;

    @NotBlank
    @Size(max = 120)
    private String titulo;

    @Valid
    @NotNull
    @JsonIncludeProperties({ "id", "nome" })
    private PessoaDTO diretor;

    @NotNull
    private LocalDate lancamento;

    @NotNull
    @Size(min = 1)
    @JsonIncludeProperties({ "id", "nome" })
    private List<@Valid Genero> generos;

    @NotNull
    @PositiveOrZero
    private Integer classificacao;

    @NotNull
    @Positive
    private Integer duracaoMinutos;

    @NotNull
    @Size(min = 1)
    @JsonIncludeProperties({ "id", "nome" })
    private List<@Valid PessoaDTO> elenco;

    @NotBlank
    @Size(max = 80)
    private String distribuidora;

    @Size(max = 255)
    private String capaUrl;

    @Size(max = 255)
    private String trailerUrl;
}

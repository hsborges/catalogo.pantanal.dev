package dev.pantanal.catalogo.filmes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.validator.constraints.URL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmeCreateDTO {

    @NotBlank(message = "O título é obrigatório.")
    @Size(min = 1, max = 120, message = "O título deve ter entre 1 e 120 caracteres.")
    private String titulo;

    @NotNull(message = "O id do diretor é obrigatório.")
    private Long diretor;

    @NotNull(message = "A data de lançamento é obrigatória.")
    private LocalDate lancamento;

    @NotEmpty(message = "A lista de gêneros não pode ser vazia.")
    private List<@NotNull(message = "O id do gênero é obrigatório.") Long> generos;

    @NotBlank(message = "A classificação é obrigatória.")
    private String classificacao;

    @NotNull(message = "A duração é obrigatória.")
    @Positive(message = "A duração deve ser maior que zero.")
    private Integer duracaoMinutos;

    @NotEmpty(message = "O elenco não pode ser vazio.")
    private List<@NotNull(message = "O id do integrante do elenco é obrigatório.") Long> elenco;

    @NotBlank(message = "A distribuidora é obrigatória.")
    private String distribuidora;

    @URL(message = "A URL da capa é obrigatória.")
    private String capaUrl;

    @URL(message = "A URL do trailer é obrigatória.")
    private String trailerUrl;
}

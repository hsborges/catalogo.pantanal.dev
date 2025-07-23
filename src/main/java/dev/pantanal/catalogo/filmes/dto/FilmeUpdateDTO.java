package dev.pantanal.catalogo.filmes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmeUpdateDTO {
    @Size(max = 120, message = "O título deve ter no máximo 120 caracteres.")
    private String titulo;

    private Long diretor;

    private LocalDate lancamento;

    private List<Long> generos;

    @Positive(message = "A classificação deve ser um valor positivo.")
    private Integer classificacao;

    @Positive(message = "A duração deve ser um valor positivo.")
    private Integer duracaoMinutos;

    private List<Long> elenco;

    private String distribuidora;

    @URL(message = "A URL da capa deve ser válida.")
    private String capaUrl;

    @URL(message = "A URL do trailer deve ser válida.")
    private String trailerUrl;
}

package dev.pantanal.catalogo.pessoas.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotNull;
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
public class PessoaDTO {
    private Long id;

    @Size(min = 1, max = 80)
    private String nome;

    @NotNull
    private LocalDate dataNascimento;

    @Size(min = 1, max = 2000)
    private String biografia;

    @URL
    private String fotoUrl;
}

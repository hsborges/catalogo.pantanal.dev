package dev.pantanal.catalogo.pessoas;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    @Size(max = 80)
    private String nome;

    @NotNull
    private LocalDate dataNascimento;

    @Size(max = 1000)
    private String biografia;

    @Size(max = 255)
    private String fotoUrl;
}

package dev.pantanal.catalogo.generos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneroDTO {
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String nome;
}

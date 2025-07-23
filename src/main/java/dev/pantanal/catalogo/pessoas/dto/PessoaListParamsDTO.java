
package dev.pantanal.catalogo.pessoas.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;

@Data
@NoArgsConstructor
public class PessoaListParamsDTO {
    private String nome;

    @Min(value = 0, message = "Página deve ser zero ou maior")
    private int page = 0;

    @Min(value = 1, message = "Tamanho deve ser pelo menos 1")
    private int size = 10;
}

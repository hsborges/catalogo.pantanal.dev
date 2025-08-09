
package dev.pantanal.catalogo.pessoas.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
public class PessoaListParamsDTO {

    @Schema(description = "Filtro por nome (case-insensitive, contém)", example = "joao")
    private String nome;

    @Min(value = 0, message = "Página deve ser zero ou maior")
    @Schema(description = "Número da página (0-based)", example = "0", minimum = "0")
    private int page = 0;

    @Min(value = 1, message = "Tamanho deve ser pelo menos 1")
    @Schema(description = "Tamanho da página", example = "10", minimum = "1")
    private int size = 10;
}

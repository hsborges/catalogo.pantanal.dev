package dev.pantanal.catalogo.pessoas.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PessoaCreateDTO {
    @NotNull(message = "Nome é obrigatório")
    @Size(min = 1, max = 80, message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Data de nascimento é obrigatória")
    private LocalDate dataNascimento;

    @NotNull(message = "Biografia é obrigatória")
    @Size(min = 1, max = 2000, message = "Biografia deve ter no máximo 2000 caracteres")
    private String biografia;

    @URL(message = "A foto deve ser uma URL válida")
    private String fotoUrl;
}

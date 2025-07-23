package dev.pantanal.catalogo.pessoas.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PessoaUpdateDTO {
    private String nome;

    private LocalDate dataNascimento;

    @Size(max = 2000, message = "Biografia deve ter no máximo 2000 caracteres")
    private String biografia;

    @URL(message = "A foto deve ser uma URL válida")
    private String fotoUrl;
}

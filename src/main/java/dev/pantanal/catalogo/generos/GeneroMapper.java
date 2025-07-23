package dev.pantanal.catalogo.generos;

public class GeneroMapper {
    public static GeneroDTO toDTO(Genero genero) {
        if (genero == null)
            return null;
        return GeneroDTO.builder()
                .id(genero.getId())
                .nome(genero.getNome())
                .build();
    }

    public static Genero toEntity(GeneroDTO dto) {
        if (dto == null)
            return null;
        return Genero.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .build();
    }
}

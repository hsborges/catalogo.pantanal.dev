package dev.pantanal.catalogo.generos;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GeneroMapper {
    GeneroDTO toDTO(Genero genero);

    Genero toEntity(GeneroDTO dto);
}

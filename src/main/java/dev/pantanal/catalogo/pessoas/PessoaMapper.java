package dev.pantanal.catalogo.pessoas;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import dev.pantanal.catalogo.pessoas.dto.PessoaDTO;
import dev.pantanal.catalogo.pessoas.dto.PessoaCreateDTO;
import dev.pantanal.catalogo.pessoas.dto.PessoaUpdateDTO;

@Mapper(componentModel = "spring")
public interface PessoaMapper {
    PessoaDTO toDTO(Pessoa pessoa);

    @Mapping(target = "id", ignore = true)
    Pessoa toEntity(PessoaDTO dto);

    @Mapping(target = "id", ignore = true)
    Pessoa toEntity(PessoaCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Pessoa pessoa, PessoaUpdateDTO dto);
}

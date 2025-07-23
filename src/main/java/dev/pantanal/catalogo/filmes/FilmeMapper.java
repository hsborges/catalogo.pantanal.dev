
package dev.pantanal.catalogo.filmes;

import dev.pantanal.catalogo.filmes.dto.FilmeCreateDTO;

import dev.pantanal.catalogo.filmes.dto.FilmeDTO;
import dev.pantanal.catalogo.filmes.dto.FilmeUpdateDTO;
import dev.pantanal.catalogo.pessoas.PessoaMapper;
import dev.pantanal.catalogo.pessoas.Pessoa;
import dev.pantanal.catalogo.generos.Genero;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { PessoaMapper.class })
public interface FilmeMapper {

    @Named("idsToGeneros")
    static List<Genero> idsToGeneros(List<Long> ids) {
        return ids == null ? null
                : ids.stream().map(id -> Genero.builder().id(id).build()).collect(Collectors.toList());
    }

    @Named("idToPessoa")
    static Pessoa idToPessoa(Long id) {
        return id == null ? null : Pessoa.builder().id(id).build();
    }

    @Named("idsToPessoas")
    static List<Pessoa> idsToPessoas(List<Long> ids) {
        return ids == null ? null
                : ids.stream().map(id -> Pessoa.builder().id(id).build()).collect(Collectors.toList());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "generos", source = "generos", qualifiedByName = "idsToGeneros")
    @Mapping(target = "diretor", source = "diretor", qualifiedByName = "idToPessoa")
    @Mapping(target = "elenco", source = "elenco", qualifiedByName = "idsToPessoas")
    Filme fromCreateDTO(FilmeCreateDTO dto);

    FilmeDTO toDTO(Filme filme);

    Filme toEntity(FilmeDTO dto);

    void updateEntity(@MappingTarget Filme filme, FilmeDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "generos", source = "generos", qualifiedByName = "idsToGeneros")
    @Mapping(target = "diretor", source = "diretor", qualifiedByName = "idToPessoa")
    @Mapping(target = "elenco", source = "elenco", qualifiedByName = "idsToPessoas")
    void updateEntity(@MappingTarget Filme filme, FilmeUpdateDTO dto);

}

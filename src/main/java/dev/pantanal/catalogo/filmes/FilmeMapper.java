package dev.pantanal.catalogo.filmes;

import java.util.stream.Collectors;

import dev.pantanal.catalogo.generos.Genero;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import dev.pantanal.catalogo.pessoas.PessoaMapper;

@Mapper(componentModel = "spring")
public abstract class FilmeMapper {

    @Autowired
    protected PessoaMapper pessoaMapper;

    public FilmeDTO toDTO(Filme filme) {
        if (filme == null)
            return null;
        return FilmeDTO.builder()
                .id(filme.getId())
                .titulo(filme.getTitulo())
                .diretor(pessoaMapper.toDTO(filme.getDiretor()))
                .lancamento(filme.getLancamento())
                .generos(filme.getGeneros() != null
                        ? filme.getGeneros().stream().map(Genero::getNome)
                                .collect(Collectors.toList())
                        : null)
                .classificacao(filme.getClassificacao())
                .duracaoMinutos(filme.getDuracaoMinutos())
                .elenco(filme.getElenco() != null
                        ? filme.getElenco().stream().map(pessoaMapper::toDTO)
                                .collect(Collectors.toList())
                        : null)
                .distribuidora(filme.getDistribuidora())
                .capaUrl(filme.getCapaUrl())
                .trailerUrl(filme.getTrailerUrl())
                .build();
    }

    public Filme toEntity(FilmeDTO dto) {
        if (dto == null)
            return null;
        return Filme.builder()
                .titulo(dto.getTitulo())
                .diretor(pessoaMapper.toEntity(dto.getDiretor()))
                .lancamento(dto.getLancamento())
                .generos(dto.getGeneros() != null
                        ? dto.getGeneros().stream()
                                .map(nome -> Genero.builder().nome(nome).build())
                                .collect(Collectors.toList())
                        : null)
                .classificacao(dto.getClassificacao())
                .duracaoMinutos(dto.getDuracaoMinutos())
                .elenco(dto.getElenco() != null
                        ? dto.getElenco().stream().map(pessoaMapper::toEntity)
                                .collect(Collectors.toList())
                        : null)
                .distribuidora(dto.getDistribuidora())
                .capaUrl(dto.getCapaUrl())
                .trailerUrl(dto.getTrailerUrl())
                .build();
    }

    public void updateEntity(@MappingTarget Filme filme, FilmeDTO dto) {
        filme.setTitulo(dto.getTitulo());
        filme.setDiretor(pessoaMapper.toEntity(dto.getDiretor()));
        filme.setLancamento(dto.getLancamento());
        filme.setGeneros(dto.getGeneros() != null
                ? dto.getGeneros().stream().map(nome -> Genero.builder().nome(nome).build())
                        .collect(Collectors.toList())
                : null);
        filme.setClassificacao(dto.getClassificacao());
        filme.setDuracaoMinutos(dto.getDuracaoMinutos());
        filme.setElenco(dto.getElenco() != null
                ? dto.getElenco().stream().map(pessoaMapper::toEntity).collect(Collectors.toList())
                : null);
        filme.setDistribuidora(dto.getDistribuidora());
        filme.setCapaUrl(dto.getCapaUrl());
        filme.setTrailerUrl(dto.getTrailerUrl());
    }

}

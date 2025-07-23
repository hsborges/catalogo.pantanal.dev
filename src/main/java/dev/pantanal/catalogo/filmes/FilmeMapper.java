package dev.pantanal.catalogo.filmes;

public class FilmeMapper {

    public static FilmeDTO toDTO(Filme filme) {
        if (filme == null)
            return null;
        return FilmeDTO.builder()
                .id(filme.getId())
                .titulo(filme.getTitulo())
                .diretor(filme.getDiretor())
                .lancamento(filme.getLancamento())
                .generos(filme.getGeneros())
                .classificacao(filme.getClassificacao())
                .duracaoMinutos(filme.getDuracaoMinutos())
                .elenco(filme.getElenco())
                .distribuidora(filme.getDistribuidora())
                .capaUrl(filme.getCapaUrl())
                .trailerUrl(filme.getTrailerUrl())
                .build();
    }

    public static Filme toEntity(FilmeDTO dto) {
        if (dto == null)
            return null;
        return Filme.builder()
                .titulo(dto.getTitulo())
                .diretor(dto.getDiretor())
                .lancamento(dto.getLancamento())
                .generos(dto.getGeneros())
                .classificacao(dto.getClassificacao())
                .duracaoMinutos(dto.getDuracaoMinutos())
                .elenco(dto.getElenco())
                .distribuidora(dto.getDistribuidora())
                .capaUrl(dto.getCapaUrl())
                .trailerUrl(dto.getTrailerUrl())
                .build();
    }

    public static void updateEntity(Filme filme, FilmeDTO dto) {
        filme.setTitulo(dto.getTitulo());
        filme.setDiretor(dto.getDiretor());
        filme.setLancamento(dto.getLancamento());
        filme.setGeneros(dto.getGeneros());
        filme.setClassificacao(dto.getClassificacao());
        filme.setDuracaoMinutos(dto.getDuracaoMinutos());
        filme.setElenco(dto.getElenco());
        filme.setDistribuidora(dto.getDistribuidora());
        filme.setCapaUrl(dto.getCapaUrl());
        filme.setTrailerUrl(dto.getTrailerUrl());
    }
}

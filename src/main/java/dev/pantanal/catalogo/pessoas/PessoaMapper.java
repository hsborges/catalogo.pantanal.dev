package dev.pantanal.catalogo.pessoas;

public class PessoaMapper {
    public static PessoaDTO toDTO(Pessoa pessoa) {
        if (pessoa == null)
            return null;
        return PessoaDTO.builder()
                .id(pessoa.getId())
                .nome(pessoa.getNome())
                .dataNascimento(pessoa.getDataNascimento())
                .biografia(pessoa.getBiografia())
                .fotoUrl(pessoa.getFotoUrl())
                .build();
    }

    public static Pessoa toEntity(PessoaDTO dto) {
        if (dto == null)
            return null;
        return Pessoa.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .dataNascimento(dto.getDataNascimento())
                .biografia(dto.getBiografia())
                .fotoUrl(dto.getFotoUrl())
                .build();
    }

    public static void updateEntity(Pessoa pessoa, PessoaDTO dto) {
        pessoa.setNome(dto.getNome());
        pessoa.setDataNascimento(dto.getDataNascimento());
        pessoa.setBiografia(dto.getBiografia());
        pessoa.setFotoUrl(dto.getFotoUrl());
    }
}

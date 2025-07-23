package dev.pantanal.catalogo.generos;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GeneroService {
    private final GeneroRepository generoRepository;
    private final GeneroMapper generoMapper;

    public GeneroService(GeneroRepository generoRepository, GeneroMapper generoMapper) {
        this.generoRepository = generoRepository;
        this.generoMapper = generoMapper;
    }

    public List<GeneroDTO> listarTodos() {
        return generoRepository.findAll().stream()
                .map(generoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<GeneroDTO> buscarPorId(Long id) {
        return generoRepository.findById(id).map(generoMapper::toDTO);
    }

    public Optional<Genero> buscarEntidadePorId(Long id) {
        return generoRepository.findById(id);
    }

    public boolean existsByNome(String nome) {
        return generoRepository.existsByNome(nome);
    }

    public GeneroDTO criar(GeneroDTO dto) {
        Genero genero = generoMapper.toEntity(dto);
        Genero salvo = generoRepository.save(genero);
        return generoMapper.toDTO(salvo);
    }

    public Optional<GeneroDTO> atualizar(Long id, GeneroDTO dto) {
        return generoRepository.findById(id).map(genero -> {
            genero.setNome(dto.getNome());
            generoRepository.save(genero);
            return generoMapper.toDTO(genero);
        });
    }

    public boolean deletar(Long id) {
        if (!generoRepository.existsById(id)) {
            return false;
        }
        generoRepository.deleteById(id);
        return true;
    }
}


package dev.pantanal.catalogo.filmes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FilmeService {

    private final FilmeRepository filmeRepository;
    private final FilmeMapper filmeMapper;

    public FilmeService(FilmeRepository filmeRepository, FilmeMapper filmeMapper) {
        this.filmeRepository = filmeRepository;
        this.filmeMapper = filmeMapper;
    }

    public List<FilmeDTO> listarTodos() {
        return filmeRepository.findAll().stream()
                .map(filmeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<FilmeDTO> buscarPorId(Long id) {
        return filmeRepository.findById(id).map(filmeMapper::toDTO);
    }

    public FilmeDTO criar(FilmeDTO dto) {
        Filme filme = filmeMapper.toEntity(dto);
        return filmeMapper.toDTO(filmeRepository.save(filme));
    }

    public Optional<FilmeDTO> atualizar(Long id, FilmeDTO dto) {
        return filmeRepository.findById(id).map(filme -> {
            filmeMapper.updateEntity(filme, dto);
            return filmeMapper.toDTO(filmeRepository.save(filme));
        });
    }

    public boolean deletar(Long id) {
        if (filmeRepository.existsById(id)) {
            filmeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<FilmeDTO> buscarPorTitulo(String nome) {
        return filmeRepository.findByTituloContainingIgnoreCase(nome).stream()
                .map(filmeMapper::toDTO)
                .collect(Collectors.toList());
    }
}

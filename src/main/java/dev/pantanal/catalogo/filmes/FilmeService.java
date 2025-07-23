
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

    public FilmeService(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    public List<FilmeDTO> listarTodos() {
        return filmeRepository.findAll().stream()
                .map(FilmeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<FilmeDTO> buscarPorId(Long id) {
        return filmeRepository.findById(id).map(FilmeMapper::toDTO);
    }

    public FilmeDTO criar(FilmeDTO dto) {
        Filme filme = FilmeMapper.toEntity(dto);
        return FilmeMapper.toDTO(filmeRepository.save(filme));
    }

    public Optional<FilmeDTO> atualizar(Long id, FilmeDTO dto) {
        return filmeRepository.findById(id).map(filme -> {
            FilmeMapper.updateEntity(filme, dto);
            return FilmeMapper.toDTO(filmeRepository.save(filme));
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
                .map(FilmeMapper::toDTO)
                .collect(Collectors.toList());
    }
}

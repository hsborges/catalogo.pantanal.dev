
package dev.pantanal.catalogo.filmes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.pantanal.catalogo.filmes.dto.FilmeDTO;
import dev.pantanal.catalogo.filmes.dto.FilmeUpdateDTO;
import dev.pantanal.catalogo.filmes.dto.FilmeCreateDTO;

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

    public FilmeDTO criar(FilmeCreateDTO dto) {
        Filme filme = filmeMapper.fromCreateDTO(dto);
        return filmeMapper.toDTO(filmeRepository.save(filme));
    }

    public Optional<FilmeDTO> atualizar(Long id, FilmeUpdateDTO dto) {
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

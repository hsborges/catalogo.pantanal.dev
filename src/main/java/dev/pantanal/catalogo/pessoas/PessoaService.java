package dev.pantanal.catalogo.pessoas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.pantanal.catalogo.pessoas.dto.PessoaDTO;
import dev.pantanal.catalogo.pessoas.dto.PessoaCreateDTO;
import dev.pantanal.catalogo.pessoas.dto.PessoaUpdateDTO;

@Service
@Transactional
public class PessoaService {
    private final PessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper;

    public PessoaService(PessoaRepository pessoaRepository, PessoaMapper pessoaMapper) {
        this.pessoaRepository = pessoaRepository;
        this.pessoaMapper = pessoaMapper;
    }

    public PessoaDTO criar(PessoaCreateDTO dto) {
        Pessoa pessoa = pessoaMapper.toEntity(dto);
        return pessoaMapper.toDTO(pessoaRepository.save(pessoa));
    }

    public Page<PessoaDTO> listarTodos(Pageable pageable) {
        return pessoaRepository.findAll(pageable).map(pessoaMapper::toDTO);
    }

    public Page<PessoaDTO> buscarPorNomeIgnoreCase(String nome, Pageable pageable) {
        return pessoaRepository.findByNomeContainingIgnoreCase(nome, pageable).map(pessoaMapper::toDTO);
    }

    public Optional<PessoaDTO> buscarPorId(Long id) {
        return pessoaRepository.findById(id).map(pessoaMapper::toDTO);
    }

    public Optional<PessoaDTO> atualizar(Long id, PessoaUpdateDTO dto) {
        return pessoaRepository.findById(id).map(pessoa -> {
            pessoaMapper.updateEntity(pessoa, dto);
            return pessoaMapper.toDTO(pessoaRepository.save(pessoa));
        });
    }

    public boolean deletar(Long id) {
        if (pessoaRepository.existsById(id)) {
            pessoaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public PessoaDTO buscarPorNomeIgnoreCase(String nome) {
        Pessoa pessoa = pessoaRepository.findByNome(nome);
        return pessoa != null ? pessoaMapper.toDTO(pessoa) : null;
    }
}

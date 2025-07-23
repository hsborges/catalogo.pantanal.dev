package dev.pantanal.catalogo.pessoas;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PessoaService {
    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public List<PessoaDTO> listarTodos() {
        return pessoaRepository.findAll().stream()
                .map(PessoaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<PessoaDTO> buscarPorId(Long id) {
        return pessoaRepository.findById(id).map(PessoaMapper::toDTO);
    }

    public PessoaDTO criar(PessoaDTO dto) {
        Pessoa pessoa = PessoaMapper.toEntity(dto);
        return PessoaMapper.toDTO(pessoaRepository.save(pessoa));
    }

    public Optional<PessoaDTO> atualizar(Long id, PessoaDTO dto) {
        return pessoaRepository.findById(id).map(pessoa -> {
            PessoaMapper.updateEntity(pessoa, dto);
            return PessoaMapper.toDTO(pessoaRepository.save(pessoa));
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
        return pessoa != null ? PessoaMapper.toDTO(pessoa) : null;
    }
}

package com.uneb.labweb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.uneb.labweb.model.Especialidade;
import com.uneb.labweb.repository.EspecialidadeRepository;

@Service
public class EspecialidadeService {
    
    private final EspecialidadeRepository especialidadeRepository;

    public EspecialidadeService(EspecialidadeRepository especialidadeRepository) {
        this.especialidadeRepository = especialidadeRepository;
    }

    public Especialidade criarEspecialidade(Especialidade especialidade) {
        return especialidadeRepository.save(especialidade);
    }

    public List<Especialidade> buscarTodasEspecialidades() {
        return especialidadeRepository.findAll();
    }

    public Optional<Especialidade> buscarEspecialidadePorId(Long id) {
        return especialidadeRepository.findById(id);
    }

    public Especialidade atualizarEspecialidade(Especialidade especialidade) {
        return especialidadeRepository.save(especialidade);
    }

    public void deletarEspecialidade(Long id) {
        especialidadeRepository.deleteById(id);
    }
}
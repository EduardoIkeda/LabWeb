package com.uneb.labweb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uneb.labweb.model.PostoSaude;
import com.uneb.labweb.repository.PostoSaudeRepository;

@Service
public class PostoSaudeService {

    @Autowired
    private PostoSaudeRepository postoSaudeRepository;

    public PostoSaude criarPostoSaude(PostoSaude postoSaude) {
        return postoSaudeRepository.save(postoSaude);
    }

    public List<PostoSaude> buscarTodosPostosSaude() {
        return postoSaudeRepository.findAll();
    }

    public Optional<PostoSaude> buscarPostoSaudePorId(Long id) {
        return postoSaudeRepository.findById(id);
    }

    public PostoSaude atualizarPostoSaude(PostoSaude postoSaude) {
        return postoSaudeRepository.save(postoSaude);
    }

    public void deletarPostoSaude(Long id) {
        postoSaudeRepository.deleteById(id);
    }
}
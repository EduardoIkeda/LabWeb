package com.uneb.labweb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.uneb.labweb.model.Penalizacao;
import com.uneb.labweb.repository.PenalizacaoRepository;

@Service
public class PenalizacaoService {

    private final PenalizacaoRepository penalizacaoRepository;

    public PenalizacaoService(PenalizacaoRepository penalizacaoRepository) {
        this.penalizacaoRepository = penalizacaoRepository;
    }

    public Penalizacao criarPenalizacao(Penalizacao penalizacao) {
        return penalizacaoRepository.save(penalizacao);
    }

    public List<Penalizacao> buscarTodasPenalizacoes() {
        return penalizacaoRepository.findAll();
    }

    public Optional<Penalizacao> buscarPenalizacaoPorId(Long id) {
        return penalizacaoRepository.findById(id);
    }

    public Penalizacao atualizarPenalizacao(Penalizacao penalizacao) {
        return penalizacaoRepository.save(penalizacao);
    }

    public void deletarPenalizacao(Long id) {
        penalizacaoRepository.deleteById(id);
    }
}
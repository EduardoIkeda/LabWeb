package com.uneb.labweb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uneb.labweb.model.Penalizacao;
import com.uneb.labweb.service.PenalizacaoService;

@Validated
@RestController
@RequestMapping("/api/penalizacoes")
public class PenalizacaoController {

    private final PenalizacaoService penalizacaoService;

    public PenalizacaoController(PenalizacaoService penalizacaoService) {
        this.penalizacaoService = penalizacaoService;
    }

    @GetMapping
    public ResponseEntity<List<Penalizacao>> buscarTodasPenalizacoes() {
        List<Penalizacao> penalizacoes = penalizacaoService.buscarTodasPenalizacoes();
        return ResponseEntity.ok(penalizacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Penalizacao> buscarPenalizacaoPorId(@PathVariable Long id) {
        Optional<Penalizacao> penalizacao = penalizacaoService.buscarPenalizacaoPorId(id);
        return penalizacao.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Penalizacao> criarPenalizacao(@RequestBody Penalizacao penalizacao) {
        Penalizacao novaPenalizacao = penalizacaoService.criarPenalizacao(penalizacao);
        return ResponseEntity.ok(novaPenalizacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Penalizacao> atualizarPenalizacao(@PathVariable Long id, @RequestBody Penalizacao penalizacao) {
        penalizacao.setId(id);
        Penalizacao penalizacaoAtualizada = penalizacaoService.atualizarPenalizacao(penalizacao);
        return ResponseEntity.ok(penalizacaoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPenalizacao(@PathVariable Long id) {
        penalizacaoService.deletarPenalizacao(id);
        return ResponseEntity.noContent().build();
    }
}
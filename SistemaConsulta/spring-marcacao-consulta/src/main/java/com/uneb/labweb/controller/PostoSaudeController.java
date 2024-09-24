package com.uneb.labweb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uneb.labweb.model.PostoSaude;
import com.uneb.labweb.service.PostoSaudeService;

@RestController
@RequestMapping("/api/postos-saude")
public class PostoSaudeController {

    @Autowired
    private PostoSaudeService postoSaudeService;

    @PostMapping
    public ResponseEntity<PostoSaude> criarPostoSaude(@RequestBody PostoSaude postoSaude) {
        PostoSaude novoPostoSaude = postoSaudeService.criarPostoSaude(postoSaude);
        return ResponseEntity.ok(novoPostoSaude);
    }

    @GetMapping
    public ResponseEntity<List<PostoSaude>> buscarTodosPostosSaude() {
        List<PostoSaude> postosSaude = postoSaudeService.buscarTodosPostosSaude();
        return ResponseEntity.ok(postosSaude);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostoSaude> buscarPostoSaudePorId(@PathVariable Long id) {
        Optional<PostoSaude> postoSaude = postoSaudeService.buscarPostoSaudePorId(id);
        return postoSaude.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostoSaude> atualizarPostoSaude(@PathVariable Long id, @RequestBody PostoSaude postoSaude) {
        postoSaude.setId(id);
        PostoSaude postoSaudeAtualizado = postoSaudeService.atualizarPostoSaude(postoSaude);
        return ResponseEntity.ok(postoSaudeAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPostoSaude(@PathVariable Long id) {
        postoSaudeService.deletarPostoSaude(id);
        return ResponseEntity.noContent().build();
    }
}
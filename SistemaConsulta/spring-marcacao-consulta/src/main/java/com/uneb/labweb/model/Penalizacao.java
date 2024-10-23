package com.uneb.labweb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Penalizacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long usuarioId;
    private LocalDateTime dataInicioPenalizacao;
    private LocalDateTime dataFimPenalizacao;

    public Penalizacao() {
    }

    //#region Getters and Setters
    //Teste exemplo sobre a penalização

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDateTime getDataInicioPenalizacao() {
        return dataInicioPenalizacao;
    }

    public void setDataInicioPenalizacao(LocalDateTime dataInicioPenalizacao) {
        this.dataInicioPenalizacao = dataInicioPenalizacao;
    }

    public LocalDateTime getDataFimPenalizacao() {
        return dataFimPenalizacao;
    }

    public void setDataFimPenalizacao(LocalDateTime dataFimPenalizacao) {
        this.dataFimPenalizacao = dataFimPenalizacao;
    }

}

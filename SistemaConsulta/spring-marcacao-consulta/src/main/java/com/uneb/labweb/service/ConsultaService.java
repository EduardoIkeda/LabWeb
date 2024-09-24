package com.uneb.labweb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uneb.labweb.model.Consulta;
import com.uneb.labweb.repository.ConsultaRepository;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    public Consulta criarConsulta(Consulta consulta) {
        return consultaRepository.save(consulta);
    }

    public List<Consulta> buscarTodasConsultas() {
        return consultaRepository.findAll();
    }

    public Optional<Consulta> buscarConsultaPorId(Long id) {
        return consultaRepository.findById(id);
    }

    public Consulta atualizarConsulta(Consulta consulta) {
        return consultaRepository.save(consulta);
    }

    public void deletarConsulta(Long id) {
        consultaRepository.deleteById(id);
    }
}
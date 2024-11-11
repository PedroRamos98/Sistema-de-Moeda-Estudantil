package org.example.moedaestudantecombd.service;

import org.example.moedaestudantecombd.model.Vantagem;
import org.example.moedaestudantecombd.repository.VantagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VantagemService {

    @Autowired
    private VantagemRepository vantagemRepository;

    public List<Vantagem> listarTodas() {
        return vantagemRepository.findAll();
    }
}
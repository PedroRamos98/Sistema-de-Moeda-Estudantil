package org.example.moedaestudantecombd.service;

import org.example.moedaestudantecombd.model.Extrato;
import org.example.moedaestudantecombd.repository.ExtratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtratoService {

    @Autowired
    private ExtratoRepository extratoRepository;

    public List<Extrato> listarExtratosPorAluno(Long alunoId) {
        return extratoRepository.findByAlunoId(alunoId);
    }

    public List<Extrato> listarExtratosPorProfessor(Long professorId) {
        return extratoRepository.findByProfessorId(professorId);
    }
}
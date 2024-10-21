package org.example.moedaestudantecombd.service;

import org.example.moedaestudantecombd.model.Aluno;
import org.example.moedaestudantecombd.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public Aluno salvarAluno(Aluno aluno) {
        return alunoRepository.save(aluno);
    }
}

package org.example.moedaestudantecombd.service;

import org.example.moedaestudantecombd.model.Aluno;
import org.example.moedaestudantecombd.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public void salvarAluno(Aluno aluno) {

        alunoRepository.save(aluno);
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
    }

    public void removerAluno(Long id) {
        alunoRepository.deleteById(id);
    }
}
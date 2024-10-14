package org.example.moedaestudantecombd.service;

import org.example.moedaestudantecombd.DAO.AlunoDAO;
import org.example.moedaestudantecombd.model.Aluno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    @Autowired
    private AlunoDAO alunoDAO;

    public List<Aluno> buscarTodosAlunos() {
        return alunoDAO.findAll();
    }

    public Aluno buscarAlunoPorId(Long id) {
        return alunoDAO.findById(id).orElse(null);
    }

    public Aluno salvarAluno(Aluno aluno) {
        return alunoDAO.save(aluno);
    }

    public void deletarAluno(Long id) {
        alunoDAO.deleteById(id);
    }
}

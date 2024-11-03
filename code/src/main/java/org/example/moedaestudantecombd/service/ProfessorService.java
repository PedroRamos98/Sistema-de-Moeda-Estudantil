package org.example.moedaestudantecombd.service;

import org.example.moedaestudantecombd.model.Aluno;
import org.example.moedaestudantecombd.model.Extrato;
import org.example.moedaestudantecombd.model.Professor;
import org.example.moedaestudantecombd.repository.AlunoRepository;
import org.example.moedaestudantecombd.repository.ExtratoRepository;
import org.example.moedaestudantecombd.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ExtratoRepository extratoRepository;

    public void salvarProfessor(Professor professor) {
        professorRepository.save(professor);
    }

    public List<Professor> listarTodos() {
        return professorRepository.findAll();
    }

    public Professor buscarPorId(Long id) {
        return professorRepository.findById(id).orElse(null);
    }

    public void removerProfessor(Long id) {
        professorRepository.deleteById(id);
    }

    public void atualizarProfessor(Professor professor) {
        professorRepository.save(professor);
    }

    public void adicionarMoedas(Long id, Integer moedas) {
        Professor professor = professorRepository.findById(id).orElse(null);
        if (professor != null) {
            professor.setMoedas(professor.getMoedas() + moedas);
            professorRepository.save(professor);
        }
    }

    public void adicionarMoedasParaTodos(int quantidade) {
        List<Professor> professores = professorRepository.findAll();
        for (Professor professor : professores) {
            professor.setMoedas(professor.getMoedas() + quantidade);
        }
        professorRepository.saveAll(professores);
    }

    public void distribuirPontos(Long professorId, Long alunoId, int quantidade, String descricao) {
        Professor professor = professorRepository.findById(professorId).orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        Aluno aluno = alunoRepository.findById(alunoId).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        if (professor.getMoedas() >= quantidade) {
            professor.setMoedas(professor.getMoedas() - quantidade);
            aluno.setMoedas(aluno.getMoedas() + quantidade);
            professorRepository.save(professor);
            alunoRepository.save(aluno);

            // Salvar a transação no extrato
            Extrato extrato = new Extrato();
            extrato.setProfessor(professor);
            extrato.setAluno(aluno);
            extrato.setDescricao(descricao);
            extrato.setMoedasgastas(quantidade);
            extrato.setData(LocalDateTime.now().toString());
            extratoRepository.save(extrato);
        } else {
            throw new RuntimeException("Saldo insuficiente");
        }
    }
}
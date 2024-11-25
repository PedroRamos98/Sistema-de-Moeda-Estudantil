package org.example.moedaestudantecombd.service;

import org.example.moedaestudantecombd.model.Aluno;
import org.example.moedaestudantecombd.model.Extrato;
import org.example.moedaestudantecombd.model.Vantagem;
import org.example.moedaestudantecombd.model.VantagensResgatadas;
import org.example.moedaestudantecombd.repository.AlunoRepository;
import org.example.moedaestudantecombd.repository.ExtratoRepository;
import org.example.moedaestudantecombd.repository.VantagemRepository;
import org.example.moedaestudantecombd.repository.VantagensResgatadasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private VantagemRepository vantagemRepository;

    @Autowired
    private VantagensResgatadasRepository vantagensResgatadasRepository;

    @Autowired
    private ExtratoRepository extratoRepository;

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

    public Aluno login(String email, String senha) {
        return alunoRepository.findByEmailAndSenha(email, senha);
    }

    public List<Vantagem> listarVantagens() {
        return vantagemRepository.findAll();
    }

    public boolean usarVantagem(Aluno aluno, Long vantagemId) {
        Vantagem vantagem = vantagemRepository.findById(vantagemId).orElseThrow(() -> new IllegalArgumentException("Vantagem não encontrada"));
        if (aluno.getMoedas() >= vantagem.getCusto()) {
            aluno.setMoedas(aluno.getMoedas() - (int) vantagem.getCusto());
            alunoRepository.save(aluno);

            VantagensResgatadas resgatada = new VantagensResgatadas();
            resgatada.setAluno(aluno);
            resgatada.setVantagem(vantagem);
            resgatada.setCusto(vantagem.getCusto());
            resgatada.setFotoVantagem(vantagem.getFotoProduto());
            vantagensResgatadasRepository.save(resgatada);

            Extrato extrato = new Extrato();
            extrato.setAluno(aluno);
            extrato.setDescricao("Resgate de vantagem: " + vantagem.getDescricao());
            extrato.setMoedasgastas((int) vantagem.getCusto());
            extrato.setData(LocalDateTime.now().toString());
            extratoRepository.save(extrato);

            return true;
        } else {
            return false;
        }
    }
}
package org.example.moedaestudantecombd.controller;

import org.example.moedaestudantecombd.model.Aluno;
import org.example.moedaestudantecombd.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping("/cadastro")
    public ResponseEntity<Aluno> cadastrarAluno(@ModelAttribute Aluno aluno) {
        Aluno novoAluno = alunoService.salvarAluno(aluno);
        return new ResponseEntity<>(novoAluno, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public String listarAlunos(Model model) {
        List<Aluno> alunos = alunoService.listarTodos();
        model.addAttribute("alunos", alunos);
        return "alunos";
    }
}
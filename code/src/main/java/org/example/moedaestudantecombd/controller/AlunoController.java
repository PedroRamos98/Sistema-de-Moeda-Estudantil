package org.example.moedaestudantecombd.controller;

import org.example.moedaestudantecombd.model.Aluno;
import org.example.moedaestudantecombd.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping("/cadastro")
    public ResponseEntity<Aluno> cadastrarAluno(@ModelAttribute Aluno aluno) {
        Aluno novoAluno = alunoService.salvarAluno(aluno);
        return new ResponseEntity<>(novoAluno, HttpStatus.CREATED);
    }


}
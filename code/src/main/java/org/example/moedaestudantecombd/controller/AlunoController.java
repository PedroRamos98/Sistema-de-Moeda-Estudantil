package org.example.moedaestudantecombd.controller;

import org.example.moedaestudantecombd.model.Aluno;
import org.example.moedaestudantecombd.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String cadastrarAluno(@ModelAttribute Aluno aluno) {
        alunoService.salvarAluno(aluno);
        return "redirect:/alunos/listar";
    }

    @GetMapping("/listar")
    public String listarAlunos(Model model) {
        List<Aluno> alunos = alunoService.listarTodos();
        model.addAttribute("alunos", alunos);
        return "alunos";
    }

    @GetMapping("/editar/{id}")
    public String editarAluno(@PathVariable Long id, Model model) {
        Aluno aluno = alunoService.buscarPorId(id);
        model.addAttribute("aluno", aluno);
        return "editarAluno";
    }

    @PostMapping("/atualizar")
    public String atualizarAluno(@ModelAttribute Aluno aluno) {
        alunoService.salvarAluno(aluno);
        return "redirect:/alunos/listar";
    }

    @GetMapping("/deletar/{id}")
    public String deletarAluno(@PathVariable Long id) {
        alunoService.deletarAluno(id);
        return "redirect:/alunos/listar";
    }
}
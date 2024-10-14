package org.example.moedaestudantecombd.controller;

import org.example.moedaestudantecombd.model.Aluno;
import org.example.moedaestudantecombd.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @GetMapping("/cadastro")
    public String showCadastroForm(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "aluno/cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrarAluno(Aluno aluno) {
        alunoRepository.save(aluno);
        return "redirect:/alunos/lista";
    }

    @GetMapping("/lista")
    public String listarAlunos(Model model) {
        model.addAttribute("alunos", alunoRepository.findAll());
        return "aluno/lista";
    }
}

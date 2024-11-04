package org.example.moedaestudantecombd.controller;

import org.example.moedaestudantecombd.model.Aluno;
import org.example.moedaestudantecombd.model.Extrato;
import org.example.moedaestudantecombd.repository.AlunoRepository;
import org.example.moedaestudantecombd.service.AlunoService;
import org.example.moedaestudantecombd.service.ExtratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ExtratoService extratoService;

    @GetMapping("/cadastro")
    public String mostrarCadastro() {
        return "cadastro";
    }

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
    public String mostrarFormEditar(@PathVariable Long id, Model model) {
        Aluno aluno = alunoService.buscarPorId(id);
        model.addAttribute("aluno", aluno);
        return "editarAluno";
    }

    @PostMapping("/editar/{id}")
    public String atualizarAluno(@PathVariable Long id, @ModelAttribute Aluno aluno) {
        Aluno alunoExistente = alunoService.buscarPorId(id);
        aluno.setId(id);
        aluno.setMoedas(alunoExistente.getMoedas());
        alunoService.salvarAluno(aluno);
        return "redirect:/alunos/listar";
    }

    @GetMapping("/remover/{id}")
    public String removerAluno(@PathVariable Long id) {
        alunoService.removerAluno(id);
        return "redirect:/alunos/listar";
    }

    @GetMapping("/{id}/extratos")
    public String listarExtratos(@PathVariable Long id, Model model) {
        List<Extrato> extratos = extratoService.listarExtratosPorAluno(id);
        model.addAttribute("extratos", extratos);
        return "extratos";
    }
}
package org.example.moedaestudantecombd.controller;

import org.example.moedaestudantecombd.model.Aluno;
import org.example.moedaestudantecombd.model.Extrato;
import org.example.moedaestudantecombd.model.Professor;
import org.example.moedaestudantecombd.service.AlunoService;
import org.example.moedaestudantecombd.service.ExtratoService;
import org.example.moedaestudantecombd.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private ExtratoService extratoService;

    @GetMapping("/cadastro")
    public String mostrarCadastro() {
        return "cadastroProfessor";
    }

    @PostMapping("/cadastro")
    public String cadastrarProfessor(@RequestParam String nome, @RequestParam String email, @RequestParam String cpf, @RequestParam String departamento) {
        Professor professor = new Professor();
        professor.setNome(nome);
        professor.setEmail(email);
        professor.setCpf(cpf);
        professor.setDepartamento(departamento);
        professor.setMoedas(0);
        professorService.salvarProfessor(professor);
        return "redirect:/professores/listar";
    }

    @GetMapping("/listar")
    public String listarProfessores(Model model) {
        List<Professor> professores = professorService.listarTodos();
        model.addAttribute("professores", professores);
        return "/listarProfessores";
    }

    @RequestMapping("/editar")
    public String mostrarFormEditar() {
        return "editarProfessor";
    }

    @RequestMapping("/remover")
    public String removerProfessor() {
        return "redirect:/professores/listar";
    }

    @GetMapping("/adicionarMoedas")
    public String adicionarMoedasParaTodos() {
        professorService.adicionarMoedasParaTodos(1000);
        return "redirect:/professores/listar";
    }

    @GetMapping("/distribuirPontos")
    public String mostrarFormDistribuirPontos(Model model) {
        List<Professor> professores = professorService.listarTodos();
        List<Aluno> alunos = alunoService.listarTodos(); // Certifique-se de ter um serviço para listar todos os alunos
        model.addAttribute("professores", professores);
        model.addAttribute("alunos", alunos);
        return "distribuirPontos";
    }

    @PostMapping("/distribuirPontos")
    public String distribuirPontos(@RequestParam Long professorId, @RequestParam Long alunoId, @RequestParam int quantidade, @RequestParam String descricao) {
        professorService.distribuirPontos(professorId, alunoId, quantidade, descricao);
        return "redirect:/professores/listar";
    }

    @GetMapping("/{id}/extratos")
    public String listarExtratos(@PathVariable Long id, Model model) {
        List<Extrato> extratos = extratoService.listarExtratosPorProfessor(id);
        model.addAttribute("extratos", extratos);
        return "extratosProfessor";
    }
}
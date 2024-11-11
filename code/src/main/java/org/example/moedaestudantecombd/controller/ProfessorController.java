package org.example.moedaestudantecombd.controller;

import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/login")
    public String mostrarLogin() {
        return "loginProfessor";
    }

    @PostMapping("/cadastro")
    public String cadastrarProfessor(@RequestParam String nome, @RequestParam String email, @RequestParam String cpf, @RequestParam String departamento, @RequestParam String senha) {
        Professor professor = new Professor();
        professor.setNome(nome);
        professor.setEmail(email);
        professor.setCpf(cpf);
        professor.setDepartamento(departamento);
        professor.setMoedas(0);
        professor.setSenha(senha);
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
    public String mostrarFormDistribuirPontos(Model model, HttpSession session) {
        Professor professorLogado = (Professor) session.getAttribute("professorLogado");
        if (professorLogado == null) {
            return "redirect:/professores/login";
        }
        model.addAttribute("professor", professorLogado);
        model.addAttribute("alunos", alunoService.listarTodos());
        return "distribuirPontos";
    }

    @PostMapping("/distribuirPontos")
    public String distribuirPontos(@RequestParam Long alunoId, @RequestParam int quantidade, @RequestParam String descricao, HttpSession session) {
        Professor professorLogado = (Professor) session.getAttribute("professorLogado");
        if (professorLogado == null) {
            return "redirect:/professores/login";
        }
        professorService.distribuirPontos(professorLogado.getId(), alunoId, quantidade, descricao);
        return "redirect:/professores/listar";
    }

    @GetMapping("/{id}/extratos")
    public String listarExtratos(@PathVariable Long id, Model model) {
        List<Extrato> extratos = extratoService.listarExtratosPorProfessor(id);
        model.addAttribute("extratos", extratos);
        return "extratosProfessor";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String senha, HttpSession session) {
        Professor professor = professorService.login(email, senha);
        if (professor != null) {
            session.setAttribute("professorLogado", professor);
            return "redirect:/professores/distribuirPontos";
        } else {
            return "redirect:/professores/login?error";
        }
    }
}
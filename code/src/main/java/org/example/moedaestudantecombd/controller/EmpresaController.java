package org.example.moedaestudantecombd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.example.moedaestudantecombd.model.Empresa;
import org.example.moedaestudantecombd.service.EmpresaService;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class EmpresaController {

    private static final Logger logger = LoggerFactory.getLogger(EmpresaController.class);

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/empresa/cadastro")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "cadastroEmpresa";
    }

    @PostMapping("/empresa/cadastro")
    public String cadastrarEmpresa(
            @RequestParam("nomeEmpresa") String nomeEmpresa,
            @RequestParam("descricao") String descricao,
            @RequestParam("custo") double custo,
            @RequestParam("fotoProduto") MultipartFile fotoProduto
    ) {
        try {
            logger.info("Iniciando cadastro de empresa: {}", nomeEmpresa);
            empresaService.cadastrarEmpresa(nomeEmpresa, descricao, custo, fotoProduto);
            logger.info("Empresa cadastrada com sucesso: {}", nomeEmpresa);
        } catch (IOException e) {
            logger.error("Erro ao cadastrar empresa", e);
            return "erro";
        }

        // Redireciona para a página de listagem de empresas após o cadastro
        return "redirect:/empresa/listar";
    }

    @GetMapping("/empresa/listar")
    public String listarEmpresas(Model model) {
        List<Empresa> empresas = empresaService.listarTodas();
        model.addAttribute("empresas", empresas);
        return "empresas";
    }


}

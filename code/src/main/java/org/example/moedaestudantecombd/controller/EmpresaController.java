package org.example.moedaestudantecombd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.example.moedaestudantecombd.model.Empresa;
import org.example.moedaestudantecombd.service.EmpresaService;

import java.io.IOException;
import java.util.List;

@Controller
public class EmpresaController {

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
            @RequestParam("descricao[]") List<String> descricoes,
            @RequestParam("custo[]") List<Double> custos,
            @RequestParam("fotoProduto[]") List<MultipartFile> fotosProdutos
    ) {
        try {
            empresaService.cadastrarEmpresa(nomeEmpresa, descricoes, custos, fotosProdutos);
        } catch (IOException e) {
            return "erro";
        }
        return "redirect:/empresa/listar";
    }

    @GetMapping("/empresa/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable("id") Long id, Model model) {
        Empresa empresa = empresaService.buscarPorId(id);
        model.addAttribute("empresa", empresa);
        return "editarEmpresa";
    }

    @PostMapping("/empresa/editar/{id}")
    public String editarEmpresa(
            @PathVariable("id") Long id,
            @RequestParam("nomeEmpresa") String nomeEmpresa,
            @RequestParam(value = "descricao[]", required = false) List<String> descricoes,
            @RequestParam(value = "custo[]", required = false) List<Double> custos,
            @RequestParam(value = "fotoProduto[]", required = false) List<MultipartFile> fotosProdutos,
            Model model
    ) {
        try {
            List<String> descricoesSeguras = descricoes != null ? descricoes : List.of();
            List<Double> custosSeguros = custos != null ? custos : List.of();
            List<MultipartFile> fotosSeguras = fotosProdutos != null ? fotosProdutos : List.of();

            empresaService.editarEmpresa(id, nomeEmpresa, descricoesSeguras, custosSeguros, fotosSeguras);
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao salvar alterações.");
            return "editarEmpresa";
        }
        return "redirect:/empresa/listar";
    }


    @PostMapping("/empresa/remover/{id}")
    public String removerEmpresa(@PathVariable("id") Long id) {
        empresaService.removerEmpresa(id);
        return "redirect:/empresa/listar";
    }

    @GetMapping("/empresa/listar")
    public String listarEmpresas(Model model) {
        List<Empresa> empresas = empresaService.listarTodas();
        model.addAttribute("empresas", empresas);
        return "empresas";
    }

    @PostMapping("/empresa/removerVantagem/{vantagemId}")
    public String removerVantagem(@PathVariable("vantagemId") Long vantagemId) {
        empresaService.removerVantagem(vantagemId);
        return "redirect:/empresa/listar";
    }


}

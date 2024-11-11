package org.example.moedaestudantecombd.controller;

import org.example.moedaestudantecombd.service.VantagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vantagens")
public class VantagemController {

    @Autowired
    private VantagemService vantagemService;

    @GetMapping("/listar")
    public String listarVantagens(Model model) {
        model.addAttribute("vantagens", vantagemService.listarTodas());
        return "listarVantagens";
    }
}
package org.example.moedaestudantecombd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CadastroController {

    @GetMapping("/cadastro")
    public String mostrarCadastro() {
        return "cadastro"; // Sem a extensão .html, pois o Spring sabe que ele está em templates
    }
}


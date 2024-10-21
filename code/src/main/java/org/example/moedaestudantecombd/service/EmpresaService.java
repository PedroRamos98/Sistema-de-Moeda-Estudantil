package org.example.moedaestudantecombd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.example.moedaestudantecombd.repository.EmpresaRepository;
import org.example.moedaestudantecombd.model.Empresa;
import org.example.moedaestudantecombd.model.Vantagem;
import org.example.moedaestudantecombd.repository.VantagemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Base64;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class EmpresaService {

    private static final Logger logger = LoggerFactory.getLogger(EmpresaService.class);

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private VantagemRepository vantagemRepository;

    @Transactional
    public void cadastrarEmpresa(String nomeEmpresa, String descricao, double custo, MultipartFile fotoProduto) throws IOException {
        logger.info("Criando nova empresa: {}", nomeEmpresa);

        Empresa empresa = new Empresa();
        empresa.setNome(nomeEmpresa);
        empresaRepository.save(empresa);
        logger.info("Empresa salva: {}", empresa.getId());

        Vantagem vantagem = new Vantagem();
        vantagem.setDescricao(descricao);
        vantagem.setCusto(custo);
        vantagem.setEmpresa(empresa);

        if (!fotoProduto.isEmpty()) {
            try {
                byte[] bytes = fotoProduto.getBytes();
                String base64Image = Base64.getEncoder().encodeToString(bytes);
                vantagem.setFotoProduto(base64Image);
                logger.info("Foto do produto salva como Base64");
            } catch (IOException e) {
                logger.error("Erro ao salvar a imagem do produto", e);
                throw e;
            }
        }

        vantagemRepository.save(vantagem);
        logger.info("Vantagem salva: {}", vantagem.getId());
    }

    public List<Empresa> listarTodas() {
        return empresaRepository.findAll();
    }
}
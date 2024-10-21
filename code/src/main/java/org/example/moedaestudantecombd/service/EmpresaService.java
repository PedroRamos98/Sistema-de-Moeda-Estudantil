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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class EmpresaService {

    private static final Logger logger = LoggerFactory.getLogger(EmpresaService.class);

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private VantagemRepository vantagemRepository;

    private static String UPLOADED_FOLDER = "uploads/";

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

        // Salvar a imagem
        if (!fotoProduto.isEmpty()) {
            try {
                byte[] bytes = fotoProduto.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + fotoProduto.getOriginalFilename());
                Files.write(path, bytes);
                vantagem.setFotoProduto(fotoProduto.getOriginalFilename());
                logger.info("Foto do produto salva: {}", fotoProduto.getOriginalFilename());
            } catch (IOException e) {
                logger.error("Erro ao salvar a imagem do produto", e);
                throw e;
            }
        }

        vantagemRepository.save(vantagem);
        logger.info("Vantagem salva: {}", vantagem.getId());
    }
}

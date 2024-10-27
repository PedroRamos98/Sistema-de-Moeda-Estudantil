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

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private VantagemRepository vantagemRepository;

    @Transactional
    public void cadastrarEmpresa(String nomeEmpresa, List<String> descricoes, List<Double> custos, List<MultipartFile> fotosProdutos) throws IOException {
        Empresa empresa = new Empresa();
        empresa.setNome(nomeEmpresa);
        empresaRepository.save(empresa);

        for (int i = 0; i < descricoes.size(); i++) {
            Vantagem vantagem = new Vantagem();
            vantagem.setDescricao(descricoes.get(i));
            vantagem.setCusto(custos.get(i));
            vantagem.setEmpresa(empresa);

            if (!fotosProdutos.get(i).isEmpty()) {
                byte[] bytes = fotosProdutos.get(i).getBytes();
                String base64Image = Base64.getEncoder().encodeToString(bytes);
                vantagem.setFotoProduto(base64Image);
            }

            vantagemRepository.save(vantagem);
        }
    }

    @Transactional
    public void editarEmpresa(Long id, String nomeEmpresa, List<String> descricoes, List<Double> custos, List<MultipartFile> fotosProdutos) throws IOException {
        Empresa empresa = empresaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));
        empresa.setNome(nomeEmpresa);
        empresaRepository.save(empresa);

        List<Vantagem> vantagensExistentes = empresa.getVantagens();
        for (int i = 0; i < descricoes.size(); i++) {
            Vantagem vantagem;
            if (i < vantagensExistentes.size()) {
                vantagem = vantagensExistentes.get(i);
            } else {
                vantagem = new Vantagem();
                vantagem.setEmpresa(empresa);
            }

            vantagem.setDescricao(descricoes.get(i));
            vantagem.setCusto(custos.get(i));

            if (!fotosProdutos.get(i).isEmpty()) {
                byte[] bytes = fotosProdutos.get(i).getBytes();
                String base64Image = Base64.getEncoder().encodeToString(bytes);
                vantagem.setFotoProduto(base64Image);
            }

            vantagemRepository.save(vantagem);
        }

        if (vantagensExistentes.size() > descricoes.size()) {
            for (int i = descricoes.size(); i < vantagensExistentes.size(); i++) {
                vantagemRepository.delete(vantagensExistentes.get(i));
            }
        }
    }


    @Transactional
    public void removerEmpresa(Long id) {
        empresaRepository.deleteById(id);
    }

    public Empresa buscarPorId(Long id) {
        return empresaRepository.findById(id).orElse(null);
    }

    public List<Empresa> listarTodas() {
        return empresaRepository.findAll();
    }

    public void removerVantagem(Long vantagemId) { vantagemRepository.deleteById(vantagemId); }
}


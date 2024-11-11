package org.example.moedaestudantecombd.config;

import org.example.moedaestudantecombd.model.Aluno;
import org.example.moedaestudantecombd.service.AlunoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(AlunoService alunoService) {
        return args -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    Objects.requireNonNull(getClass().getResourceAsStream("/alunos.data")), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    Aluno aluno = new Aluno();
                    aluno.setNome(data[0]);
                    aluno.setEmail(data[1]);
                    aluno.setCpf(data[2]);
                    aluno.setRg(data[3]);
                    aluno.setEndereco(data[4]);
                    aluno.setInstituicaoEnsino(data[5]);
                    aluno.setCurso(data[6]);
                    aluno.setMoedas(Integer.parseInt(data[7]));
                    aluno.setSenha(data[8]);
                    alunoService.salvarAluno(aluno);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}

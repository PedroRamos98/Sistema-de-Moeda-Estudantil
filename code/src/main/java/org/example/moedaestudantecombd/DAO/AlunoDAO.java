package org.example.moedaestudantecombd.DAO;

import org.example.moedaestudantecombd.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlunoDAO extends JpaRepository<Aluno, Long> {

    // Método para buscar alunos por nome
    List<Aluno> findByNome(String nome);

    // Método para buscar alunos por email
    Aluno findByEmail(String email);
}

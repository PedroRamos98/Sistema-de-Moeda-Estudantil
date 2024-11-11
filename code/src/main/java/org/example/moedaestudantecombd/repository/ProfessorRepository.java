package org.example.moedaestudantecombd.repository;

import org.example.moedaestudantecombd.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    Professor findByEmailAndSenha(String email, String senha);

}

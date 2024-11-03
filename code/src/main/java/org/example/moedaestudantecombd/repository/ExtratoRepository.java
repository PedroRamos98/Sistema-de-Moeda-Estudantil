package org.example.moedaestudantecombd.repository;

import org.example.moedaestudantecombd.model.Extrato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExtratoRepository extends JpaRepository<Extrato, Long> {
    List<Extrato> findByAlunoId(Long alunoId);
    List<Extrato> findByProfessorId(Long professorId);
}
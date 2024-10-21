package org.example.moedaestudantecombd.repository;
import org.example.moedaestudantecombd.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}

package org.example.moedaestudantecombd.repository;

import org.example.moedaestudantecombd.model.VantagensResgatadas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VantagensResgatadasRepository extends JpaRepository<VantagensResgatadas, Long> {
}
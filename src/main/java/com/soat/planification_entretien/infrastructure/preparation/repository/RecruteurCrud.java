package com.soat.planification_entretien.infrastructure.preparation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruteurCrud extends JpaRepository<Recruteur, Integer> {
    Recruteur findByEmail(String email);
}

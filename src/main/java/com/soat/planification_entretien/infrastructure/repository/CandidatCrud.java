package com.soat.planification_entretien.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatCrud extends JpaRepository<Candidat, UUID> {
}

package com.soat.planification_entretien.profil.infrastructure.repository;

import java.nio.channels.FileChannel;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatCrud extends JpaRepository<Candidat, Integer> {
    Optional<Candidat> findByUuid(UUID uuid);
}

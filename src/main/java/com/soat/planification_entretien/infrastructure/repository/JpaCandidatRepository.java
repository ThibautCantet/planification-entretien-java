package com.soat.planification_entretien.infrastructure.repository;

import com.soat.planification_entretien.infrastructure.model.Candidat;
import com.soat.planification_entretien.use_case.CandidatRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCandidatRepository extends CandidatRepository, JpaRepository<Candidat, Integer> {
}

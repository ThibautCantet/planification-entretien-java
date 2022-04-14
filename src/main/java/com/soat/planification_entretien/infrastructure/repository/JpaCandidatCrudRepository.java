package com.soat.planification_entretien.infrastructure.repository;

import com.soat.planification_entretien.infrastructure.model.Candidat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCandidatCrudRepository extends CrudRepository<Candidat, Integer> {
}

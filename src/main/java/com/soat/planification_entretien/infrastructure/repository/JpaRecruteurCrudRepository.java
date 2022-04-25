package com.soat.planification_entretien.infrastructure.repository;

import java.util.List;

import com.soat.planification_entretien.infrastructure.model.Recruteur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRecruteurCrudRepository extends CrudRepository<Recruteur, Integer> {
    List<Recruteur> findAll();
}

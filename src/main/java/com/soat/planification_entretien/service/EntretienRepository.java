package com.soat.planification_entretien.service;

import java.util.List;

import com.soat.planification_entretien.model.Entretien;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntretienRepository extends CrudRepository<Entretien, Integer> {
    List<Entretien> findAll();
}

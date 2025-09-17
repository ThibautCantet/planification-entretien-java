package com.soat.planification_entretien.recruteur.query.domain.repository;

import java.util.List;

import com.soat.planification_entretien.recruteur.query.domain.model.Recruteur;

public interface RecruteurDao {
    List<Recruteur> find10AnsExperience();
}

package com.soat.planification_entretien.domain.recruteur.query.dao;

import java.util.List;

import com.soat.planification_entretien.domain.recruteur.query.dto.Recruteur;

public interface RecruteurDAO {

    List<Recruteur> find10AnsExperience();
}

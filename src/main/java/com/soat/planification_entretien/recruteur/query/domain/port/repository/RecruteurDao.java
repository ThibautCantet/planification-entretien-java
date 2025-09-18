package com.soat.planification_entretien.recruteur.query.domain.port.repository;

import java.util.List;

import com.soat.planification_entretien.recruteur.query.domain.model.Recruteur;
import com.soat.planification_entretien.recruteur.query.domain.model.RecruteurDetail;

public interface RecruteurDao {
    List<Recruteur> find10AnsExperience();

    void addExperimente(RecruteurDetail recruteurDetail);
}

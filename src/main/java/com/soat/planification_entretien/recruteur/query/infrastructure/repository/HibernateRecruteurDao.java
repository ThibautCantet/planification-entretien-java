package com.soat.planification_entretien.recruteur.query.infrastructure.repository;

import java.util.List;

import com.soat.planification_entretien.recruteur.query.domain.model.Recruteur;
import com.soat.planification_entretien.recruteur.query.domain.repository.RecruteurDao;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateRecruteurDao implements RecruteurDao {
    private final RecruteurRead recruteurRead;

    public HibernateRecruteurDao(RecruteurRead recruteurRead) {
        this.recruteurRead = recruteurRead;
    }

    @Override
    public List<Recruteur> find10AnsExperience() {
        return recruteurRead.findAll()
                .stream().filter(r -> r.getExperienceInYears() >= 10)
                .map(recruteur -> new Recruteur(
                        recruteur.getId(),
                        recruteur.getLanguage(),
                        recruteur.getEmail(),
                        recruteur.getExperienceInYears()))
                .toList();
    }

}

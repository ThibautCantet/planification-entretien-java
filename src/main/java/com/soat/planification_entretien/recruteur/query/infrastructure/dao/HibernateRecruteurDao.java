package com.soat.planification_entretien.recruteur.query.infrastructure.dao;

import java.util.List;

import com.soat.planification_entretien.recruteur.query.application.RecruteurDao;
import com.soat.planification_entretien.recruteur.query.application.RecruteurDetail;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateRecruteurDao implements RecruteurDao {
    private final RecruteurDetailCrud recruteurDetailCrud;

    public HibernateRecruteurDao(RecruteurDetailCrud recruteurDetailCrud) {
        this.recruteurDetailCrud = recruteurDetailCrud;
    }

    @Override
    public List<RecruteurDetail> find10AnsExperience() {
        return recruteurDetailCrud.findAll()
                .stream()
                .map(recruteur -> new RecruteurDetail(recruteur.getId(),
                        recruteur.getCompetence(),
                        recruteur.getEmail()))
                .toList();
    }

    @Override
    public void addExperimente(RecruteurDetail recruteurDetail) {
        var recruteur = new JpaRecruteurDetail(recruteurDetail.id(),
                recruteurDetail.competence(),
                recruteurDetail.email());
        recruteurDetailCrud.save(recruteur);
    }
}

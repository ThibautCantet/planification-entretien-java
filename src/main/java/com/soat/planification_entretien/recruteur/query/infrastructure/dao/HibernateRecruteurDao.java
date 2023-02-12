package com.soat.planification_entretien.recruteur.query.infrastructure.dao;

import java.util.List;

import com.soat.planification_entretien.recruteur.infrastructure.repository.RecruteurCrud;
import com.soat.planification_entretien.recruteur.query.application.IRecruteurDetail;
import com.soat.planification_entretien.recruteur.query.application.RecruteurDao;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateRecruteurDao implements RecruteurDao {
    private final RecruteurCrud recruteurCrud;

    public HibernateRecruteurDao(RecruteurCrud recruteurCrud) {
        this.recruteurCrud = recruteurCrud;
    }

    @Override
    public List<IRecruteurDetail> find10AnsExperience() {
        return recruteurCrud.findAll()
                .stream().filter(r -> r.getExperienceInYears() >= 10)
                .map(recruteur -> new IRecruteurDetailImpl(
                        recruteur.getId(),
                        recruteur.getLanguage(),
                        recruteur.getExperienceInYears(),
                        recruteur.getEmail()))
                .map(r -> (IRecruteurDetail) r)
                .toList();
    }
}

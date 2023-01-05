package com.soat.planification_entretien.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import com.soat.planification_entretien.domain.RecruteurRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateRecruteurRepository implements RecruteurRepository {
    private final RecruteurCrud recruteurCrud;

    public HibernateRecruteurRepository(RecruteurCrud recruteurCrud) {
        this.recruteurCrud = recruteurCrud;
    }

    @Override
    public Optional<com.soat.planification_entretien.domain.Recruteur> findById(int recruteurId) {
        return recruteurCrud.findById(recruteurId).map(
                recruteur -> new com.soat.planification_entretien.domain.Recruteur(
                        recruteurId,
                        recruteur.getLanguage(),
                        recruteur.getEmail(),
                        recruteur.getExperienceInYears()
                )
        );
    }

    @Override
    public com.soat.planification_entretien.domain.Recruteur save(com.soat.planification_entretien.domain.Recruteur recruteur) {
        var toSave = new com.soat.planification_entretien.infrastructure.repository.Recruteur(recruteur.getLanguage(), recruteur.getEmail(), recruteur.getExperienceInYears());
        var saved = recruteurCrud.save(toSave);
        return com.soat.planification_entretien.domain.Recruteur.of(saved.getId(), recruteur);
    }

    @Override
    public List<com.soat.planification_entretien.domain.Recruteur> find10AnsExperience() {
        return recruteurCrud.findAll()
                .stream().filter(r -> r.getExperienceInYears() >= 10)
                .map(recruteur -> new com.soat.planification_entretien.domain.Recruteur(
                        recruteur.getId(),
                        recruteur.getLanguage(),
                        recruteur.getEmail(),
                        recruteur.getExperienceInYears()))
                .toList();
    }
}

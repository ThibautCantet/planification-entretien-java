package com.soat.planification_entretien.recruteur.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import com.soat.planification_entretien.recruteur.domain.port.repository.RecruteurRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateRecruteurRepository implements RecruteurRepository {
    private final RecruteurCrud recruteurCrud;

    public HibernateRecruteurRepository(RecruteurCrud recruteurCrud) {
        this.recruteurCrud = recruteurCrud;
    }

    @Override
    public Optional<com.soat.planification_entretien.recruteur.domain.model.Recruteur> findById(int recruteurId) {
        return recruteurCrud.findById(recruteurId).map(
                recruteur -> new com.soat.planification_entretien.recruteur.domain.model.Recruteur(
                        recruteurId,
                        recruteur.getLanguage(),
                        recruteur.getEmail(),
                        recruteur.getExperienceInYears()
                )
        );
    }

    @Override
    public com.soat.planification_entretien.recruteur.domain.model.Recruteur save(com.soat.planification_entretien.recruteur.domain.model.Recruteur recruteur) {
        var toSave = new Recruteur(recruteur.getLanguage(), recruteur.getAdresseEmail(), recruteur.getExperienceInYears());
        if (recruteur.getId() != null) {
            toSave.setId(recruteur.getId());
        }
        toSave.setDisponible(recruteur.estDisponible());
        var saved = recruteurCrud.save(toSave);
        return com.soat.planification_entretien.recruteur.domain.model.Recruteur.of(saved.getId(), recruteur);
    }

    @Override
    public List<com.soat.planification_entretien.recruteur.domain.model.Recruteur> find10AnsExperience() {
        return recruteurCrud.findAll()
                .stream().filter(r -> r.getExperienceInYears() >= 10)
                .map(recruteur -> new com.soat.planification_entretien.recruteur.domain.model.Recruteur(
                        recruteur.getId(),
                        recruteur.getLanguage(),
                        recruteur.getEmail(),
                        recruteur.getExperienceInYears()))
                .toList();
    }

    @Override
    public Optional<com.soat.planification_entretien.recruteur.domain.model.Recruteur> findByEmail(String email) {
        return recruteurCrud.findByEmail(email).map(
                recruteur -> new com.soat.planification_entretien.recruteur.domain.model.Recruteur(
                        recruteur.getId(),
                        recruteur.getLanguage(),
                        recruteur.getEmail(),
                        recruteur.getExperienceInYears(),
                        recruteur.getDisponible()
                )
        );
    }
}

package com.soat.planification_entretien.recruteur.infrastructure.repository;

import java.util.Optional;

import com.soat.planification_entretien.recruteur.command.domain.RecruteurRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateRecruteurRepository implements RecruteurRepository {
    private final RecruteurCrud recruteurCrud;

    public HibernateRecruteurRepository(RecruteurCrud recruteurCrud) {
        this.recruteurCrud = recruteurCrud;
    }

    @Override
    public Optional<com.soat.planification_entretien.recruteur.command.domain.Recruteur> findById(int recruteurId) {
        return recruteurCrud.findById(recruteurId).map(
                recruteur -> new com.soat.planification_entretien.recruteur.command.domain.Recruteur(
                        recruteurId,
                        recruteur.getLanguage(),
                        recruteur.getEmail(),
                        recruteur.getExperienceInYears()
                )
        );
    }

    @Override
    public com.soat.planification_entretien.recruteur.command.domain.Recruteur save(com.soat.planification_entretien.recruteur.command.domain.Recruteur recruteur) {
        var toSave = new Recruteur(recruteur.getLanguage(), recruteur.getAdresseEmail(), recruteur.getExperienceInYears());
        if (recruteur.getId() != null) {
            toSave.setId(recruteur.getId());
        }
        toSave.setDisponible(recruteur.estDisponible());
        var saved = recruteurCrud.save(toSave);
        return com.soat.planification_entretien.recruteur.command.domain.Recruteur.of(saved.getId(), recruteur);
    }


    @Override
    public Optional<com.soat.planification_entretien.recruteur.command.domain.Recruteur> findByEmail(String email) {
        return recruteurCrud.findByEmail(email).map(
                recruteur -> new com.soat.planification_entretien.recruteur.command.domain.Recruteur(
                        recruteur.getId(),
                        recruteur.getLanguage(),
                        recruteur.getEmail(),
                        recruteur.getExperienceInYears(),
                        recruteur.getDisponible()
                )
        );
    }
}

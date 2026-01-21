package com.soat.planification_entretien.recruteur.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.soat.planification_entretien.recruteur.domain.RecruteurRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateRecruteurRepository implements RecruteurRepository {
    private final RecruteurCrud recruteurCrud;

    public HibernateRecruteurRepository(RecruteurCrud recruteurCrud) {
        this.recruteurCrud = recruteurCrud;
    }

    @Override
    public Optional<com.soat.planification_entretien.recruteur.domain.Recruteur> findById(int recruteurId) {
        return recruteurCrud.findById(recruteurId).map(
                recruteur -> new com.soat.planification_entretien.recruteur.domain.Recruteur(
                        recruteurId,
                        recruteur.getLanguage(),
                        recruteur.getEmail(),
                        recruteur.getExperienceInYears(),
                        recruteur.isDisponible()
                )
        );
    }

    @Override
    public com.soat.planification_entretien.recruteur.domain.Recruteur save(com.soat.planification_entretien.recruteur.domain.Recruteur recruteur) {
        var toSave = new Recruteur(recruteur.getId(), recruteur.getLanguage(), recruteur.getEmail(), recruteur.getExperienceInYears(), recruteur.estDisponible());
        var saved = recruteurCrud.save(toSave);
        return com.soat.planification_entretien.recruteur.domain.Recruteur.of(saved.getId(), recruteur);
    }

    @Override
    public List<com.soat.planification_entretien.recruteur.domain.Recruteur> find10AnsExperience() {
        return recruteurCrud.findAll()
                .stream().filter(r -> r.getExperienceInYears() >= 10)
                .map(toRecruteur())
                .toList();
    }

    @Override
    public com.soat.planification_entretien.recruteur.domain.Recruteur findByEmail(String email) {
        return recruteurCrud.findByEmail(email).map(
                toRecruteur()).orElse(null);
    }

    private Function<Recruteur, com.soat.planification_entretien.recruteur.domain.Recruteur> toRecruteur() {
        return recruteur -> new com.soat.planification_entretien.recruteur.domain.Recruteur(
                recruteur.getId(),
                recruteur.getLanguage(),
                recruteur.getEmail(),
                recruteur.getExperienceInYears(),
                recruteur.isDisponible());
    }
}

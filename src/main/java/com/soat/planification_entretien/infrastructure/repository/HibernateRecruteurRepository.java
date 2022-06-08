package com.soat.planification_entretien.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.soat.planification_entretien.domain.recruteur.command.repository.RecruteurRepository;
import com.soat.planification_entretien.domain.recruteur.query.dao.RecruteurDAO;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateRecruteurRepository implements RecruteurRepository, RecruteurDAO {
    private final RecruteurCrud recruteurCrud;

    public HibernateRecruteurRepository(RecruteurCrud recruteurCrud) {
        this.recruteurCrud = recruteurCrud;
    }

    @Override
    public UUID next() {
        return UUID.randomUUID();
    }

    @Override
    public Optional<com.soat.planification_entretien.domain.recruteur.command.entity.Recruteur> findById(String recruteurId) {
        return recruteurCrud.findById(recruteurId).map(recruteur -> toRecruteur(recruteurId, recruteur)
        );
    }

    private com.soat.planification_entretien.domain.recruteur.command.entity.Recruteur toRecruteur(String recruteurId, Recruteur recruteur) {
        return com.soat.planification_entretien.domain.recruteur.command.entity.Recruteur.create(
                recruteurId,
                recruteur.getLanguage(),
                recruteur.getEmail(),
                recruteur.getExperienceInYears()
        );
    }

    @Override
    public com.soat.planification_entretien.domain.recruteur.command.entity.Recruteur save(com.soat.planification_entretien.domain.recruteur.command.entity.Recruteur recruteur) {
        var toSave = new com.soat.planification_entretien.infrastructure.repository.Recruteur(recruteur.getId(), recruteur.getLanguage(), recruteur.getEmail(), recruteur.getExperienceInYears());
        recruteurCrud.save(toSave);
        return recruteur;
    }

    @Override
    public List<com.soat.planification_entretien.domain.recruteur.command.entity.Recruteur> findAll() {
        return recruteurCrud.findAll().stream()
                .map(recruteur -> toRecruteur(recruteur.getId(), recruteur))
                .toList();
    }

    @Override
    public List<com.soat.planification_entretien.domain.recruteur.query.dto.Recruteur> find10AnsExperience() {
        return recruteurCrud.findAll()
                .stream().filter(r -> r.getExperienceInYears() >= 10)
                .map(recruteur -> new com.soat.planification_entretien.domain.recruteur.query.dto.Recruteur(
                        recruteur.getId(),
                        recruteur.getLanguage(),
                        recruteur.getEmail(),
                        recruteur.getExperienceInYears()))
                .toList();
    }
}

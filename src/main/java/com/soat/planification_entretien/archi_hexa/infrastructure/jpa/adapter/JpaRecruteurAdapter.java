package com.soat.planification_entretien.archi_hexa.infrastructure.jpa.adapter;

import com.soat.planification_entretien.archi_hexa.domain.model.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.port.RecruteurPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaRecruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.repository.JpaRecruteurRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaRecruteurAdapter implements RecruteurPort {
    private final JpaRecruteurRepository jpaRecruteurRepository;

    public JpaRecruteurAdapter(JpaRecruteurRepository jpaRecruteurRepository) {
        this.jpaRecruteurRepository = jpaRecruteurRepository;
    }

    @Override
    public Optional<Recruteur> findById(int id) {
        final Optional<JpaRecruteur> optionalOfRecruteur = jpaRecruteurRepository.findById(id);
        return optionalOfRecruteur.map(JpaRecruteurAdapter::toRecruteurDetail);
    }

    private static Recruteur toRecruteurDetail(JpaRecruteur recruteur) {
        return new Recruteur(
                recruteur.getId(),
                recruteur.getLanguage(),
                recruteur.getEmail(),
                recruteur.getExperienceInYears()
        );
    }
}

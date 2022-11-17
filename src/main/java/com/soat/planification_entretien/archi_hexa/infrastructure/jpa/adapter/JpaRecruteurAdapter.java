package com.soat.planification_entretien.archi_hexa.infrastructure.jpa.adapter;

import com.soat.planification_entretien.archi_hexa.domain.entity.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.port.RecruteurPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaRecruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.repository.RecruteurRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaRecruteurAdapter implements RecruteurPort {
    private final RecruteurRepository recruteurRepository;

    public JpaRecruteurAdapter(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    @Override
    public Optional<Recruteur> findById(int id) {
        final Optional<JpaRecruteur> optionalJpaRecruteur = recruteurRepository.findById(id);
        return optionalJpaRecruteur.map(JpaRecruteurAdapter::toRecruteur);
    }

    private static Recruteur toRecruteur(JpaRecruteur jpaRecruteur) {
        return new Recruteur(
                jpaRecruteur.getId(),
                jpaRecruteur.getLanguage(),
                jpaRecruteur.getEmail(),
                jpaRecruteur.getExperienceInYears()
        );
    }
}

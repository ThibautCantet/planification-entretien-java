package com.soat.planification_entretien.archi_hexa.infrastructure.jpa.adapter;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.soat.planification_entretien.archi_hexa.domain.model.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.port.RecruteurPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaRecruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.repository.JpaRecruteurRepository;

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

    @Override
    public Integer save(Recruteur recruteur) {
        JpaRecruteur jpaRecruteur = new JpaRecruteur(recruteur.language(), recruteur.email(), recruteur.experienceInYears());
        JpaRecruteur savedJpaRecruteur = jpaRecruteurRepository.save(jpaRecruteur);
        return savedJpaRecruteur.getId();
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

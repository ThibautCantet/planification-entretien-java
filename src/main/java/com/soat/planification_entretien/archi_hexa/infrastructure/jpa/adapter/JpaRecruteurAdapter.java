package com.soat.planification_entretien.archi_hexa.infrastructure.jpa.adapter;

import com.soat.planification_entretien.archi_hexa.domain.entity.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.entity.RecruteurExperimente;
import com.soat.planification_entretien.archi_hexa.domain.port.RecruteurPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaRecruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.repository.RecruteurRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Override
    public Integer save(Recruteur recruteur) {
        final JpaRecruteur jpaRecruteur = new JpaRecruteur(recruteur.language(), recruteur.email(), recruteur.experienceInYears());
        final JpaRecruteur saveJpaRecruteur = recruteurRepository.save(jpaRecruteur);
        return saveJpaRecruteur.getId();
    }

    @Override
    public List<Recruteur> findAll() {
        return recruteurRepository.findAll().stream()
                .map(JpaRecruteurAdapter::toRecruteur)
                .toList();
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

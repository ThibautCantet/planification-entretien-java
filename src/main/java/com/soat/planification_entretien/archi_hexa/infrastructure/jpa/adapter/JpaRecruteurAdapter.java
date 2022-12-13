package com.soat.planification_entretien.archi_hexa.infrastructure.jpa.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
        return optionalOfRecruteur.map(JpaRecruteurAdapter::toRecruteur);
    }

    @Override
    public Integer save(Recruteur recruteur) {
        JpaRecruteur jpaRecruteur = new JpaRecruteur(recruteur.language(), recruteur.email(), recruteur.experienceInYears());
        JpaRecruteur savedJpaRecruteur = jpaRecruteurRepository.save(jpaRecruteur);
        return savedJpaRecruteur.getId();
    }

    @Override
    public List<Recruteur> getByExperience(Integer minExperienceInYears) {
        List<JpaRecruteur> jpaRecruteurs = jpaRecruteurRepository.findByExperienceInYearsGreaterThanEqual(minExperienceInYears);
        return jpaRecruteurs.stream()
              .map(JpaRecruteurAdapter::toRecruteur)
              .toList();
    }

    private static Recruteur toRecruteur(JpaRecruteur recruteur) {
        return new Recruteur(
              recruteur.getId(),
              recruteur.getLanguage(),
              recruteur.getEmail(),
              recruteur.getExperienceInYears()
        );
    }
}

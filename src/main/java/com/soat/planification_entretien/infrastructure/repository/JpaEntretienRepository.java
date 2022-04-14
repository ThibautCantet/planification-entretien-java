package com.soat.planification_entretien.infrastructure.repository;

import com.soat.planification_entretien.domain.Candidat;
import com.soat.planification_entretien.domain.Entretien;
import com.soat.planification_entretien.domain.Recruteur;
import com.soat.planification_entretien.use_case.EntretienRepository;
import org.springframework.stereotype.Service;

@Service
public class JpaEntretienRepository implements EntretienRepository {
    private final JpaEntretienCrudRepository jpaEntretienCrudRepository;
    private final JpaCandidatCrudRepository jpaCandidatCrudRepository;
    private final JpaRecruteurCrudRepository jpaRecruteurCrudRepository;

    public JpaEntretienRepository(JpaEntretienCrudRepository jpaEntretienCrudRepository, JpaCandidatCrudRepository jpaCandidatCrudRepository, JpaRecruteurCrudRepository jpaRecruteurCrudRepository) {
        this.jpaEntretienCrudRepository = jpaEntretienCrudRepository;
        this.jpaCandidatCrudRepository = jpaCandidatCrudRepository;
        this.jpaRecruteurCrudRepository = jpaRecruteurCrudRepository;
    }

    @Override
    public Entretien findByCandidat(Candidat candidat) {
        var maybeEntretien = jpaEntretienCrudRepository.findByCandidatId(candidat.getId());

        return maybeEntretien.map(jpaEntretien -> Entretien.of(jpaEntretien.getId(),
                        Candidat.of(jpaEntretien.getCandidat().getId(), jpaEntretien.getCandidat().getLanguage(), jpaEntretien.getCandidat().getEmail(), jpaEntretien.getCandidat().getExperienceInYears()),
                        Recruteur.of(jpaEntretien.getRecruteur().getId(), jpaEntretien.getRecruteur().getLanguage(), jpaEntretien.getRecruteur().getEmail(), jpaEntretien.getRecruteur().getExperienceInYears()),
                        jpaEntretien.getHoraireEntretien()))
                .orElse(null);
    }

    @Override
    public Entretien save(Entretien entretien) {
        var jpaCandidat = jpaCandidatCrudRepository.findById(entretien.getCandidat().getId()).get();
        var jpaRecruteur = jpaRecruteurCrudRepository.findById(entretien.getRecruteur().getId()).get();

        var jpaEntretien = new com.soat.planification_entretien.infrastructure.model.Entretien(jpaCandidat,
                jpaRecruteur, entretien.getHoraireEntretien());
        var save = jpaEntretienCrudRepository.save(jpaEntretien);

        return Entretien.of(save.getId(), entretien);
    }
}

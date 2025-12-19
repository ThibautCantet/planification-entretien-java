package com.soat.planification_entretien.infrastructure.repository.adapter.jpa;

import java.util.List;

import com.soat.planification_entretien.domain.Entretien;
import com.soat.planification_entretien.domain.EntretienPort;
import com.soat.planification_entretien.infrastructure.repository.CandidatRepository;
import com.soat.planification_entretien.infrastructure.repository.EntretienRepository;
import com.soat.planification_entretien.infrastructure.repository.JpaEntretien;
import com.soat.planification_entretien.infrastructure.repository.RecruteurRepository;
import org.springframework.stereotype.Repository;

@Repository
public class EntretienAdapter implements EntretienPort {

    private final EntretienRepository entretienRepository;
    private final CandidatRepository candidatRepository;
    private final RecruteurRepository recruteurRepository;

    public EntretienAdapter(EntretienRepository entretienRepository, CandidatRepository candidatRepository, RecruteurRepository recruteurRepository) {
        this.entretienRepository = entretienRepository;
        this.candidatRepository = candidatRepository;
        this.recruteurRepository = recruteurRepository;
    }

    @Override
    public List<Entretien> findAll() {
        return entretienRepository.findAll().stream()
                .map(JpaEntretien::toEntretien)
                .toList();
    }

    @Override
    public void save(Entretien entretien) {
        var jpaCandidat = candidatRepository.findById(entretien.getCandidat().getId()).orElseThrow();
        var jpaRecruteur = recruteurRepository.findById(entretien.getRecruteur().getId()).orElseThrow();
        var jpaEntretien = new JpaEntretien(jpaCandidat, jpaRecruteur, entretien.getHoraireEntretien());
        entretienRepository.save(jpaEntretien);
    }

    @Override
    public Entretien findByCandidatId(Integer candidatId) {
        var jpaEntretien = entretienRepository.findByCandidatId(candidatId);
        if (jpaEntretien == null) {
            return null;
        }
        return jpaEntretien.toEntretien();
    }

}

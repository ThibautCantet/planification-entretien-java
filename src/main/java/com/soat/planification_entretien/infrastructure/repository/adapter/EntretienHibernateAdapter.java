package com.soat.planification_entretien.infrastructure.repository.adapter;

import java.util.Collection;
import java.util.List;

import com.soat.planification_entretien.application.use_case.output_port.EntretienPort;
import com.soat.planification_entretien.domain.model.Entretien;
import com.soat.planification_entretien.infrastructure.repository.JpaCandidat;
import com.soat.planification_entretien.infrastructure.repository.JpaEntretien;
import com.soat.planification_entretien.infrastructure.repository.JpaEntretienRepository;
import com.soat.planification_entretien.infrastructure.repository.JpaRecruteur;
import org.springframework.stereotype.Repository;

@Repository
public class EntretienHibernateAdapter implements EntretienPort {

    private final JpaEntretienRepository jpaEntretienRepository;

    public EntretienHibernateAdapter(JpaEntretienRepository jpaEntretienRepository) {
        this.jpaEntretienRepository = jpaEntretienRepository;
    }

    @Override
    public Collection<Entretien> findAll() {
        List<JpaEntretien> jpaEntretiens = jpaEntretienRepository.findAll();

        return jpaEntretiens.stream()
                .map(jpaEntretien -> new Entretien(
                        jpaEntretien.getId(),
                        jpaEntretien.getCandidat().toDomain(),
                        jpaEntretien.getRecruteur().toDomain(),
                        jpaEntretien.getHoraireEntretien()
                ))
                .toList();
    }

    @Override
    public void save(Entretien entretien) {
        var jpaEntretien = new JpaEntretien(
                JpaCandidat.fromDomain(entretien.getCandidat()),
                JpaRecruteur.fromDomain(entretien.getRecruteur()),
                entretien.getHoraireEntretien());
        jpaEntretienRepository.save(jpaEntretien);
    }
}

package com.soat.planification_entretien.infrastructure.repository.adapter;

import java.util.Optional;

import com.soat.planification_entretien.application.use_case.output_port.RecruteurPort;
import com.soat.planification_entretien.domain.model.Recruteur;
import com.soat.planification_entretien.infrastructure.repository.JpaRecruteur;
import com.soat.planification_entretien.infrastructure.repository.JpaRecruteurRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RecruteurHibernateAdapter implements RecruteurPort {

    private final JpaRecruteurRepository jpaRecruteurRepository;

    public RecruteurHibernateAdapter(JpaRecruteurRepository jpaRecruteurRepository) {
        this.jpaRecruteurRepository = jpaRecruteurRepository;
    }

    @Override
    public Optional<Recruteur> findById(int recruteurId) {
        Optional<JpaRecruteur> jpaRecruteur = jpaRecruteurRepository.findById(recruteurId);
        return jpaRecruteur.map(JpaRecruteur::toDomain);
    }

    @Override
    public int save(Recruteur recruteur) {
        var jpaRecruteur = JpaRecruteur.fromDomain(recruteur);
        JpaRecruteur saved = jpaRecruteurRepository.save(jpaRecruteur);
        return saved.getId();
    }
}

package com.soat.planification_entretien.archi_hexa.infrastructure.jpa.adapter;

import com.soat.planification_entretien.archi_hexa.domain.entity.Candidat;
import com.soat.planification_entretien.archi_hexa.domain.entity.Entretien;
import com.soat.planification_entretien.archi_hexa.domain.entity.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.port.EntretienPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaCandidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaEntretien;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaRecruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.repository.EntretienRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaEntretienAdapter implements EntretienPort {
    private final EntretienRepository entretienRepository;

    public JpaEntretienAdapter(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    @Override
    public void save(Entretien entretien) {
        final JpaCandidat jpaCandidat = toJpaCandidat(entretien.candidat());
        final JpaRecruteur jpaRecruteur = toJpaRecruteur(entretien.recruteur());
        JpaEntretien jpaEntretien = JpaEntretien.of(jpaCandidat, jpaRecruteur, entretien.dateEtHeureDisponibiliteDuRecruteur());
        entretienRepository.save(jpaEntretien);
    }

    private static JpaRecruteur toJpaRecruteur(Recruteur recruteur) {
        final JpaRecruteur jpaRecruteur = new JpaRecruteur(
                recruteur.id(),
                recruteur.language(),
                recruteur.email(),
                recruteur.experienceInYears());
        return jpaRecruteur;
    }

    private static JpaCandidat toJpaCandidat(Candidat candidat) {
        final JpaCandidat jpaCandidat = new JpaCandidat(
                candidat.id(),
                candidat.language(),
                candidat.email(),
                candidat.experienceInYears()
        );
        return jpaCandidat;
    }
}

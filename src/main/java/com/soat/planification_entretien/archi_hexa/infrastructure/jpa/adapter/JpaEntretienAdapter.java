package com.soat.planification_entretien.archi_hexa.infrastructure.jpa.adapter;

import com.soat.planification_entretien.archi_hexa.domain.model.Candidat;
import com.soat.planification_entretien.archi_hexa.domain.model.Entretien;
import com.soat.planification_entretien.archi_hexa.domain.model.EntretienDetail;
import com.soat.planification_entretien.archi_hexa.domain.model.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.port.EntretienPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaCandidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaEntretien;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaRecruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.repository.JpaEntretienRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaEntretienAdapter implements EntretienPort {
    private final JpaEntretienRepository jpaEntretienRepository;

    public JpaEntretienAdapter(JpaEntretienRepository jpaEntretienRepository) {
        this.jpaEntretienRepository = jpaEntretienRepository;
    }

    @Override
    public List<EntretienDetail> findAll() {
        return jpaEntretienRepository.findAll().stream().map(entretien ->
                new EntretienDetail(
                        entretien.getId(),
                        entretien.getCandidat().getEmail(),
                        entretien.getRecruteur().getEmail(),
                        entretien.getRecruteur().getLanguage(),
                        entretien.getHoraireEntretien())
        ).toList();
    }

    @Override
    public void save(Entretien entretienASauvegarder) {
        final Candidat candidatDetail = entretienASauvegarder.candidat();
        JpaCandidat candidat = new JpaCandidat(candidatDetail.id(), candidatDetail.language(), candidatDetail.email(), candidatDetail.experienceInYears());

        final Recruteur recruteurDetail = entretienASauvegarder.recruteur();
        JpaRecruteur recruteur = new JpaRecruteur(recruteurDetail.id(), recruteurDetail.language(), recruteurDetail.email(), recruteurDetail.experienceInYears());

        final JpaEntretien entretien = JpaEntretien.of(candidat, recruteur, entretienASauvegarder.dateEtHeureDisponibiliteDuCandidat());

        jpaEntretienRepository.save(entretien);
    }
}

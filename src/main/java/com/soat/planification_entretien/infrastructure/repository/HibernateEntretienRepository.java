package com.soat.planification_entretien.infrastructure.repository;

import java.util.List;

import com.soat.planification_entretien.domain.entretien.command.entity.Calendrier;
import com.soat.planification_entretien.domain.entretien.command.entity.Candidat;
import com.soat.planification_entretien.domain.entretien.command.entity.Recruteur;
import com.soat.planification_entretien.domain.entretien.command.entity.RendezVous;
import com.soat.planification_entretien.domain.entretien.command.repository.EntretienRepository;
import com.soat.planification_entretien.domain.rendez_vous.command.repository.CalendrierRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateEntretienRepository implements EntretienRepository {
    private final EntretienCrud entretienCrud;
    private final CandidatCrud candidatCrud;
    private final RecruteurCrud recruteurCrud;
    private final CalendrierRepository calendrierRepository;

    public HibernateEntretienRepository(EntretienCrud entretienCrud, CandidatCrud candidatCrud, RecruteurCrud recruteurCrud, CalendrierRepository calendrierRepository) {
        this.entretienCrud = entretienCrud;
        this.candidatCrud = candidatCrud;
        this.recruteurCrud = recruteurCrud;
        this.calendrierRepository = calendrierRepository;
    }

    @Override
    public int save(com.soat.planification_entretien.domain.entretien.command.entity.Entretien entretien) {

        var jpaCandidat = candidatCrud.findById(entretien.getCandidat().id()).get();
        var jpaRecruteur = recruteurCrud.findById(entretien.getRecruteur().id()).get();

        var jpaEntretien = Entretien.of(jpaCandidat,
                jpaRecruteur, entretien.getHoraireEntretien());
        Entretien saved = entretienCrud.save(jpaEntretien);

        return saved.getId();
    }

    @Override
    public com.soat.planification_entretien.domain.entretien.command.entity.Entretien findByCandidat(Candidat candidat) {
        return entretienCrud.findByCandidatId(candidat.id())
                .map(this::toEntretien)
                .orElse(null);
    }

    @Override
    public com.soat.planification_entretien.domain.entretien.command.entity.Entretien findById(int id) {
        return entretienCrud.findById(id)
                .map(this::toEntretien)
                .orElse(null);
    }

    private com.soat.planification_entretien.domain.entretien.command.entity.Entretien toEntretien(com.soat.planification_entretien.infrastructure.repository.Entretien jpaEntretien) {
        List<RendezVous> rendezVous = calendrierRepository.findByRecruteur(jpaEntretien.getRecruteur().getEmail())
                .map(Calendrier::rendezVous)
                .orElse(List.of());

        return com.soat.planification_entretien.domain.entretien.command.entity.Entretien.of(
                jpaEntretien.getId(),
                new Candidat(jpaEntretien.getId(), jpaEntretien.getCandidat().getLanguage(), jpaEntretien.getCandidat().getEmail(), jpaEntretien.getCandidat().getExperienceInYears()),
                new Recruteur(jpaEntretien.getId(), jpaEntretien.getRecruteur().getLanguage(), jpaEntretien.getRecruteur().getEmail(), jpaEntretien.getRecruteur().getExperienceInYears(), rendezVous),
                jpaEntretien.getHoraireEntretien());
    }
}

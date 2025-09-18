package com.soat.planification_entretien.entretien.command.infrastructure.repository;

import java.util.Optional;

import com.soat.planification_entretien.entretien.command.domain.model.Entretien;
import com.soat.planification_entretien.entretien.command.domain.port.repository.EntretienRepository;
import com.soat.planification_entretien.entretien.command.domain.model.Status;
import com.soat.planification_entretien.entretien.command.domain.model.Candidat;
import com.soat.planification_entretien.entretien.command.domain.model.Recruteur;
import com.soat.planification_entretien.candidat.command.infrastructure.repository.CandidatCrud;
import com.soat.planification_entretien.entretien.infrastructure.repository.HibernateEntretien;
import com.soat.planification_entretien.recruteur.command.infrastructure.repository.RecruteurCrud;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateEntretienRepository implements EntretienRepository {
    private final EntretienCrud entretienCrud;
    private final CandidatCrud candidatCrud;
    private final RecruteurCrud recruteurCrud;

    public HibernateEntretienRepository(EntretienCrud entretienCrud, CandidatCrud candidatCrud, RecruteurCrud recruteurCrud) {
        this.entretienCrud = entretienCrud;
        this.candidatCrud = candidatCrud;
        this.recruteurCrud = recruteurCrud;
    }

    @Override
    public void save(Entretien entretien) {

        var jpaCandidat = candidatCrud.findById(entretien.getCandidat().id()).get();
        var jpaRecruteur = recruteurCrud.findById(entretien.getRecruteur().id()).get();

        if (entretien.getId() == null) {
            var jpaEntretien = HibernateEntretien.of(jpaCandidat,
                    jpaRecruteur,
                    entretien.getHoraireEntretien(),
                    entretien.getStatusValue());
            entretienCrud.save(jpaEntretien);
        } else {
            Optional<HibernateEntretien> maybeEntretien = entretienCrud.findById(entretien.getId());

            maybeEntretien.ifPresentOrElse(e -> {
                        e.setCandidat(jpaCandidat);
                        e.setRecruteur(jpaRecruteur);
                        e.setStatus(entretien.getStatusValue());
                        e.setHoraireEntretien(entretien.getHoraireEntretien());
                        entretienCrud.save(e);
                    },
                    () -> {
                        var jpaEntretien = HibernateEntretien.of(jpaCandidat,
                                jpaRecruteur,
                                entretien.getHoraireEntretien(),
                                entretien.getStatusValue());
                        entretienCrud.save(jpaEntretien);
                    });
        }
    }

    @Override
    public Entretien findByCandidatId(int candidatId) {
        var maybeEntretien = entretienCrud.findByCandidat_Id(candidatId);

        return maybeEntretien
                .map(HibernateEntretienRepository::toEntretien)
                .orElse(null);
    }

    @Override
    public Optional<Entretien> findById(int entretienId) {
        var maybeEntretien = entretienCrud.findById(entretienId);

        return maybeEntretien
                .map(HibernateEntretienRepository::toEntretien);
    }

    public static Entretien toEntretien(HibernateEntretien jpaEntretien) {
             return Entretien.of(
                     jpaEntretien.getId(),
                     new Candidat(jpaEntretien.getCandidat().getId(), jpaEntretien.getCandidat().getLanguage(), jpaEntretien.getCandidat().getEmail(), jpaEntretien.getCandidat().getExperienceInYears()),
                     new Recruteur(jpaEntretien.getRecruteur().getId(), jpaEntretien.getRecruteur().getLanguage(), jpaEntretien.getRecruteur().getEmail(), jpaEntretien.getRecruteur().getExperienceInYears()),
                     jpaEntretien.getHoraireEntretien(),
                     Status.values()[jpaEntretien.getStatus()]);
         }
}

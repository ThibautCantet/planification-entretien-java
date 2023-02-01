package com.soat.planification_entretien.entretien.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import com.soat.planification_entretien.entretien.domain.EntretienRepository;
import com.soat.planification_entretien.entretien.domain.Status;
import com.soat.planification_entretien.entretien.domain.Candidat;
import com.soat.planification_entretien.entretien.domain.Recruteur;
import com.soat.planification_entretien.candidat.infrastructure.repository.CandidatCrud;
import com.soat.planification_entretien.recruteur.infrastructure.repository.RecruteurCrud;
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
    public void save(com.soat.planification_entretien.entretien.domain.Entretien entretien) {

        var jpaCandidat = candidatCrud.findById(entretien.getCandidat().id()).get();
        var jpaRecruteur = recruteurCrud.findById(entretien.getRecruteur().id()).get();

        if (entretien.getId() == null) {
            var jpaEntretien = Entretien.of(jpaCandidat,
                    jpaRecruteur,
                    entretien.getHoraireEntretien(),
                    entretien.getStatusValue());
            entretienCrud.save(jpaEntretien);
        } else {
            Optional<Entretien> maybeEntretien = entretienCrud.findById(entretien.getId());

            maybeEntretien.ifPresentOrElse(e -> {
                        e.setCandidat(jpaCandidat);
                        e.setRecruteur(jpaRecruteur);
                        e.setStatus(entretien.getStatusValue());
                        e.setHoraireEntretien(entretien.getHoraireEntretien());
                        entretienCrud.save(e);
                    },
                    () -> {
                        var jpaEntretien = Entretien.of(jpaCandidat,
                                jpaRecruteur,
                                entretien.getHoraireEntretien(),
                                entretien.getStatusValue());
                        entretienCrud.save(jpaEntretien);
                    });
        }
    }

    @Override
    public List<com.soat.planification_entretien.entretien.domain.Entretien> findAll() {
        return entretienCrud.findAll().stream()
                .map(HibernateEntretienRepository::toEntretien)
                .toList();
    }

    @Override
    public com.soat.planification_entretien.entretien.domain.Entretien findByCandidatId(int candidatId) {
        var maybeEntretien = entretienCrud.findByCandidat_Id(candidatId);

        return maybeEntretien
                .map(HibernateEntretienRepository::toEntretien)
                .orElse(null);
    }

    @Override
    public Optional<com.soat.planification_entretien.entretien.domain.Entretien> findById(int entretienId) {
        var maybeEntretien = entretienCrud.findById(entretienId);

        return maybeEntretien
                .map(HibernateEntretienRepository::toEntretien);
    }

    public static com.soat.planification_entretien.entretien.domain.Entretien toEntretien(Entretien jpaEntretien) {
        return com.soat.planification_entretien.entretien.domain.Entretien.of(
                jpaEntretien.getId(),
                new Candidat(jpaEntretien.getCandidat().getId(), jpaEntretien.getCandidat().getLanguage(), jpaEntretien.getCandidat().getEmail(), jpaEntretien.getCandidat().getExperienceInYears()),
                new Recruteur(jpaEntretien.getRecruteur().getId(), jpaEntretien.getRecruteur().getLanguage(), jpaEntretien.getRecruteur().getEmail(), jpaEntretien.getRecruteur().getExperienceInYears()),
                jpaEntretien.getHoraireEntretien(),
                Status.values()[jpaEntretien.getStatus()]);
    }
}

package com.soat.planification_entretien.entretien.infrastructure.respository;

import java.util.List;

import com.soat.planification_entretien.entretien.domain.Candidat;
import com.soat.planification_entretien.entretien.domain.ConsultantRecruteur;
import com.soat.planification_entretien.entretien.domain.EntretienRepository;
import com.soat.planification_entretien.profil.infrastructure.repository.CandidatCrud;
import com.soat.planification_entretien.profil.infrastructure.repository.RecruteurCrud;
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

        var jpaCandidat = candidatCrud.findByUuid(entretien.getCandidat().id()).get();
        var jpaRecruteur = recruteurCrud.findById(entretien.getRecruteur().id()).get();

        var jpaEntretien = Entretien.of(jpaCandidat,
                jpaRecruteur, entretien.getHoraireEntretien());
        entretienCrud.save(jpaEntretien);
    }

    @Override
    public List<com.soat.planification_entretien.entretien.domain.Entretien> findAll() {
        return entretienCrud.findAll().stream()
                .map(HibernateEntretienRepository::toEntretien)
                .toList();
    }

    @Override
    public com.soat.planification_entretien.entretien.domain.Entretien findByEmail(String email) {
        var maybeEntretien = entretienCrud.findByCandidat_Email(email);

        return maybeEntretien
                .map(HibernateEntretienRepository::toEntretien)
                .orElse(null);
    }

    private static com.soat.planification_entretien.entretien.domain.Entretien toEntretien(Entretien jpaEntretien) {
        return com.soat.planification_entretien.entretien.domain.Entretien.of(
                jpaEntretien.getId(),
                new Candidat(jpaEntretien.getCandidat().getUuid(), jpaEntretien.getCandidat().getLanguage(), jpaEntretien.getCandidat().getEmail(), jpaEntretien.getCandidat().getExperienceInYears()),
                new ConsultantRecruteur(jpaEntretien.getId(), jpaEntretien.getRecruteur().getLanguage(), jpaEntretien.getRecruteur().getEmail(), jpaEntretien.getRecruteur().getExperienceInYears()),
                jpaEntretien.getHoraireEntretien());
    }
}

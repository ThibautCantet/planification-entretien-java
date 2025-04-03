package com.soat.planification_entretien.entretien.infrastructure.respository;

import java.util.Optional;
import java.util.UUID;

import com.soat.planification_entretien.entretien.domain.Candidat;
import com.soat.planification_entretien.entretien.domain.CandidatRepository;
import com.soat.planification_entretien.profil.infrastructure.repository.CandidatCrud;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateCandidatRepository implements CandidatRepository {
    private final CandidatCrud candidatCrud;

    public HibernateCandidatRepository(CandidatCrud candidatCrud) {
        this.candidatCrud = candidatCrud;
    }

    @Override
    public Optional<Candidat> findById(UUID id) {
        return candidatCrud.findByUuid(id)
                .map(candidat -> new Candidat(candidat.getUuid(),
                        candidat.getLanguage(),
                        candidat.getEmail(),
                        candidat.getExperienceInYears()));
    }
}

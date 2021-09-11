package com.soat.candidat.use_case;

import com.soat.candidat.domain.Candidat;
import com.soat.candidat.domain.CandidatRepository;

import java.util.UUID;

public class CreerCandidat {
    private final CandidatRepository candidatRepository;

    public CreerCandidat(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    public UUID execute(String language, String email, int experienceEnAnnees) {
        final UUID id = candidatRepository.next();
        var candidat = new Candidat(id, language, email, experienceEnAnnees);
        candidatRepository.save(candidat);

        return id;
    }
}

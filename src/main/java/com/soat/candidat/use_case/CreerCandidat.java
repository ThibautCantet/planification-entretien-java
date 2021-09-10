package com.soat.candidat.use_case;

import com.soat.candidat.domain.Candidat;
import com.soat.candidat.domain.CandidatRepository;

import java.util.UUID;

public class CreerCandidat {
    private final CandidatRepository candidatRepository;

    public CreerCandidat(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    public void execute(String language, String email, int experienceEnAnnees) {
        var candidat = new Candidat(candidatRepository.next(), language, email, experienceEnAnnees);
        candidatRepository.save(candidat);
    }
}

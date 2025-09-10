package com.soat.planification_entretien.use_case.peparation;

import com.soat.planification_entretien.domain.preparation.Candidat;
import com.soat.planification_entretien.domain.preparation.CandidatRepository;
import org.springframework.stereotype.Service;

@Service
public class CreerCandidat {

    private final CandidatRepository candidatRepository;

    public CreerCandidat(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    public Integer execute(String language, String email, String experienceEnAnnees) {
        try {
            var candidat = Candidat.create(language, email, Integer.parseInt(experienceEnAnnees));

            Candidat savedCandidat = candidatRepository.save(candidat);

            return savedCandidat.getId();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}

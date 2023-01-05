package com.soat.planification_entretien.use_case;

import com.soat.planification_entretien.domain.candidat.Candidat;
import com.soat.planification_entretien.domain.candidat.CandidatRepository;
import org.springframework.stereotype.Service;

@Service
public class CreerCandidat {

    private final CandidatRepository candidatRepository;

    public CreerCandidat(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    public Integer execute(String language, String email, String experienceEnAnnees) {
        try {
            Candidat candidat = new Candidat(language, email, Integer.parseInt(experienceEnAnnees));

            Candidat savedCandidat = candidatRepository.save(candidat);

            return savedCandidat.getId();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}

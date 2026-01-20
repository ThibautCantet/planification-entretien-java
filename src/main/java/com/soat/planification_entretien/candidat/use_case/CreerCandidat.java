package com.soat.planification_entretien.candidat.use_case;

import com.soat.planification_entretien.candidat.domain.CandidatProspect;
import com.soat.planification_entretien.candidat.domain.CandidatRepository;
import org.springframework.stereotype.Service;

@Service
public class CreerCandidat {

    private final CandidatRepository candidatRepository;

    public CreerCandidat(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    public Integer execute(String language, String email, String experienceEnAnnees) {
        try {
            CandidatProspect candidat = new CandidatProspect(language, email, Integer.parseInt(experienceEnAnnees));

            CandidatProspect savedCandidat = candidatRepository.save(candidat);

            return savedCandidat.getId();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}

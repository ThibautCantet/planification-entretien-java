package com.soat.candidat.use_case;

import java.util.UUID;

import com.soat.candidat.domain.Candidat;
import com.soat.candidat.domain.CandidatRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CreerCandidat {
    private final CandidatRepository candidatRepository;

    public CreerCandidat(@Qualifier("candidat") CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    public Candidat execute(String language, String email, Integer experienceEnAnnees) {
        try {
            final UUID id = candidatRepository.next();
            Candidat candidat = new Candidat(id, language, email, experienceEnAnnees);

            return candidatRepository.save(candidat);
        } catch (Exception e) {
            return null;
        }
    }
}

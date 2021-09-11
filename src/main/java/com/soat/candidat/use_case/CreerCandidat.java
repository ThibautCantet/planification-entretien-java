package com.soat.candidat.use_case;

import com.soat.candidat.domain.Candidat;
import com.soat.candidat.domain.CandidatRepository;
import com.soat.candidat.domain.InvalidLanguage;
import com.soat.candidat.event.CreationCandidatEchouee;
import com.soat.candidat.event.CreationCandidatReussie;
import com.soat.candidat.event.ResultatCreationCandidat;

import java.util.UUID;

public class CreerCandidat {
    private final CandidatRepository candidatRepository;

    public CreerCandidat(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    public ResultatCreationCandidat execute(String language, String email, int experienceEnAnnees) {
        final UUID id = candidatRepository.next();
        try {
            var candidat = new Candidat(id, language, email, experienceEnAnnees);
            candidatRepository.save(candidat);
            return new CreationCandidatReussie(id);
        } catch (InvalidLanguage e) {
            return new CreationCandidatEchouee(id, "Techno invalide : language");
        }

    }
}

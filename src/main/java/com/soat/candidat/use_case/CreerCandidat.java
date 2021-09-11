package com.soat.candidat.use_case;

import com.soat.candidat.domain.*;
import com.soat.candidat.event.CreationCandidatEchouee;
import com.soat.candidat.event.CreationCandidatReussie;
import com.soat.candidat.event.ResultatCreationCandidat;

import java.util.UUID;

public class CreerCandidat {
    private final CandidatRepository candidatRepository;

    public CreerCandidat(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    public ResultatCreationCandidat execute(String language, String email, Integer experienceEnAnnees) {
        final UUID id = candidatRepository.next();
        try {
            var candidat = new Candidat(id, language, email, experienceEnAnnees);
            candidatRepository.save(candidat);
            return new CreationCandidatReussie(id);
        } catch (InvalidLanguage e) {
            return new CreationCandidatEchouee(id, "Techno invalide : language");
        } catch (InvalidAnneeExperience e) {
            return new CreationCandidatEchouee(id, "Années d'expérience invalide");
        } catch (InvalidEmail e) {
            return new CreationCandidatEchouee(id, "Email invalide");
        }

    }
}

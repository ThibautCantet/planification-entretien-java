package com.soat.recruteur.use_case;

import com.soat.recruteur.domain.*;
import com.soat.recruteur.event.CreationRecruteurEchouee;
import com.soat.recruteur.event.CreationRecruteurReussie;
import com.soat.recruteur.event.ResultatCreationRecruteur;

import java.util.UUID;

public class CreerRecruteur {
    private final RecruteurRepository recruteurRepository;

    public CreerRecruteur(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public ResultatCreationRecruteur execute(String language, String email, Integer experienceEnAnnees) {
        final UUID id = recruteurRepository.next();
        try {
            var recruteur = new Recruteur(id, language, email, experienceEnAnnees);
            recruteurRepository.save(recruteur);

            return new CreationRecruteurReussie(id);
        } catch (InvalidLanguage e) {
            return new CreationRecruteurEchouee(id, "Techno invalide : name");
        } catch (InvalidAnneesExperience e) {
            return new CreationRecruteurEchouee(id, "Années d'expérience invalide");
        } catch (InvalidEmail e) {
            return new CreationRecruteurEchouee(id, "Email invalide");
        }
    }
}

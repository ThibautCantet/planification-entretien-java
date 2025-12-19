package com.soat.planification_entretien.use_case;

import com.soat.planification_entretien.domain.Recruteur;
import com.soat.planification_entretien.domain.RecruteurPort;
import org.springframework.stereotype.Service;

@Service
public class CreerRecruteur {
    private final RecruteurPort recruteurPort;

    public CreerRecruteur(RecruteurPort recruteurPort) {
        this.recruteurPort = recruteurPort;
    }

    public Recruteur execute(String language, String email, String experienceEnAnnees) {

        var recruteur = Recruteur.of(language, email, experienceEnAnnees);

        if (recruteur == null) {
            return null;
        }

        return recruteurPort.save(recruteur);
    }
}

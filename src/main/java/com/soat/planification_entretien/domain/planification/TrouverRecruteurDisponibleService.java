package com.soat.planification_entretien.domain.planification;

import java.util.List;
import java.util.Optional;



public class TrouverRecruteurDisponibleService {

    public Optional<RecruteurPossible> trouver(CandidatSuivi candidat, List<RecruteurPossible> recruteurs) {
        return recruteurs.stream()
                .filter(RecruteurPossible::disponible)
                .filter(r -> r.peutEvaluer(candidat))
                .findFirst();
    }

}

package com.soat.planification_entretien.domain.planification;

import com.soat.planification_entretien.domain.Profil;

public record RecruteurPossible(Integer id, String email, Profil profil, boolean disponible) {
    public boolean peutEvaluer(CandidatSuivi candidat) {
        return profil().peutEvaluer(candidat.profil());
    }
    public boolean estDisponible() {return disponible;}
}

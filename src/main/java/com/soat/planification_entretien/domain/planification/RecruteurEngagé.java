package com.soat.planification_entretien.domain.planification;

import com.soat.planification_entretien.domain.Profil;

public record RecruteurEngagé(Integer id, String email, Profil profil) {
    public boolean peutEvaluer(CandidatSuivi candidat) {
        return profil().peutEvaluer(candidat.profil());
    }
}

package com.soat.planification_entretien.domain;

public record Profil(String langage, Integer annéeExperience) {
    public boolean peutEvaluer(Profil profilAEvaluer) {
        return langage.equals(profilAEvaluer.langage) && annéeExperience >= profilAEvaluer.annéeExperience;
    }
}

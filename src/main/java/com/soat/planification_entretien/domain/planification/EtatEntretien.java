package com.soat.planification_entretien.domain.planification;

import java.util.List;

public enum EtatEntretien {
    BROUILLON,
    PLANIFIE,
    VALIDE;

    private List<EtatEntretien> etatsSuivants;

    static {
        BROUILLON.etatsSuivants = List.of(PLANIFIE);
        PLANIFIE.etatsSuivants = List.of(VALIDE);
        VALIDE.etatsSuivants = List.of();
    }

    public boolean peutEvoluerVers(EtatEntretien nouvelEtat) {
        return etatsSuivants.contains(nouvelEtat);
    }
}

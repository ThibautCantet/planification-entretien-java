package com.soat.planification_entretien.entretien.domain;

import java.util.List;

public class CompatibilitéConsultantRecruteur {
    public static List<ConsultantRecruteur> find(List<ConsultantRecruteur> consultants, Candidat candidat) {
        return consultants.stream()
                .filter(consultantRecruteur -> consultantRecruteur.estCompatible(candidat))
                .toList();

    }
}

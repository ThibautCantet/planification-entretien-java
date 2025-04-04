package com.soat.planification_entretien.entretien.domain;

import java.util.List;

public class DisponibilitéConsultantRecruteur {
    public static List<ConsultantRecruteur> find(List<ConsultantRecruteur> consultants) {
        return consultants.stream().filter(consultantRecruteur -> consultantRecruteur.disponible())
                .toList();
    }
}

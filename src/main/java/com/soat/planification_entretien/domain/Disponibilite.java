package com.soat.planification_entretien.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

record Disponibilite(LocalDateTime horaire) {
    boolean verifier(LocalDate disponibilite) {
        return horaire.toLocalDate().equals(disponibilite);
    }
}

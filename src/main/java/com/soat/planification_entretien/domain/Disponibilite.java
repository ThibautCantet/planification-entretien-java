package com.soat.planification_entretien.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Disponibilite(LocalDateTime horaire) {
    public boolean verifier(LocalDate disponibilite) {
        return horaire.toLocalDate().equals(disponibilite);
    }
}

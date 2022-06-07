package com.soat.planification_entretien.domain.entretien.command.entity;

import java.time.LocalDateTime;

public record Creneau(LocalDateTime disponibiliteRecruteur, LocalDateTime horairePropose) {
    boolean estDisponible() {
        return disponibiliteRecruteur.equals(horairePropose);
    }
}

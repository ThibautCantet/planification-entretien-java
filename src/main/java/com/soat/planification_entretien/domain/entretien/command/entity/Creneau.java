package com.soat.planification_entretien.domain.entretien.command.entity;

import java.time.LocalDateTime;

public record Creneau(java.util.List<RendezVous> rendezVous, LocalDateTime horairePropose) {
    boolean estDisponible() {
        return rendezVous.stream()
                .noneMatch(rdv -> horairePropose.equals(rdv.horaire()) || (horairePropose.isAfter(rdv.horaire()) && horairePropose.isBefore(horairePropose.plusHours(1))));
    }
}

package com.soat.planification_entretien.domain.entretien;

import java.time.LocalDateTime;

import com.soat.planification_entretien.cqrs.Event;

public record EntretienPlanifie(int id, String emailCandidat, String emailRecruteur,
                                LocalDateTime horaireEntretien) implements Event {
}


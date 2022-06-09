package com.soat.planification_entretien.entretien.event;

import java.time.LocalDateTime;

import com.soat.planification_entretien.cqrs.Event;

public record EntretienPlanifie(String id, String emailCandidat, String emailRecruteur,
                                LocalDateTime horaireEntretien) implements Event {
}


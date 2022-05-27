package com.soat.planification_entretien.domain.entretien.event;

import java.time.LocalDateTime;

import com.soat.planification_entretien.cqrs.Event;

public record EntretienPlanifie(String emailCandidat, String emailRecruteur,
                                LocalDateTime horaire) implements Event {
}

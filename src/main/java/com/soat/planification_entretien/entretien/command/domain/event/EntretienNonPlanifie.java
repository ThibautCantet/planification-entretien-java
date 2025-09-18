package com.soat.planification_entretien.entretien.command.domain.event;

import com.soat.planification_entretien.common.cqrs.event.Event;

public record EntretienNonPlanifie(Integer id) implements Event {
}

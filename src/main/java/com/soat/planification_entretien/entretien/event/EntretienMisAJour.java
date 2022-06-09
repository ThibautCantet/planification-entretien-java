package com.soat.planification_entretien.entretien.event;

import com.soat.planification_entretien.cqrs.Event;

public record EntretienMisAJour(Integer id, String status) implements Event {
}

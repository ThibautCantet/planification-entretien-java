package com.soat.planification_entretien.rendez_vous.event;

import com.soat.planification_entretien.cqrs.Event;

public record RendezAjoute(Integer id) implements Event {
}

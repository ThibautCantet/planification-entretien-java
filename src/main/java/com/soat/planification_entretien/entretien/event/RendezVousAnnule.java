package com.soat.planification_entretien.entretien.event;

import java.time.LocalDateTime;

import com.soat.planification_entretien.cqrs.Event;

public record RendezVousAnnule(Integer id, LocalDateTime horaire) implements Event {
}

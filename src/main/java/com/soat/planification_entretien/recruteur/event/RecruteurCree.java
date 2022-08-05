package com.soat.planification_entretien.recruteur.event;

import com.soat.planification_entretien.cqrs.Event;

public record RecruteurCree(String id) implements Event {
}

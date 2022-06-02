package com.soat.planification_entretien.cqrs;


import com.soat.planification_entretien.infrastructure.middleware.Event;

public record CommandResponse<T, E extends Event>(T value, E event) {
    public CommandResponse(E event) {
        this(null, event);
    }
}

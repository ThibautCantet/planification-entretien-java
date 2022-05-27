package com.soat.planification_entretien.cqrs;


public record CommandResponse<T, E extends Event>(T value, E event) {
    public CommandResponse(E event) {
        this(null, event);
    }
}

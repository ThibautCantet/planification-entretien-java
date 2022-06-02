package com.soat.planification_entretien.cqrs;

public record QueryResponse<T>(T value, Event event) {
    public QueryResponse(Event event) {
        this(null, event);
    }
}

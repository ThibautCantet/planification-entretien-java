package com.soat.planification_entretien.cqrs;

import com.soat.planification_entretien.infrastructure.middleware.Event;

public record QueryResponse<T>(T value, Event event) {
    public QueryResponse(Event event) {
        this(null, event);
    }
}

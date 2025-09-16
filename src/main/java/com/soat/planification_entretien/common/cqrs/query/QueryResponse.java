package com.soat.planification_entretien.common.cqrs.query;

import com.soat.planification_entretien.common.cqrs.event.Event;

public record QueryResponse<T>(T value, Event event) {
    public QueryResponse(Event event) {
        this(null, event);
    }
}

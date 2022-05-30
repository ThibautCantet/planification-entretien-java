package com.soat.planification_entretien.cqrs;

public class QueryResponse<T> {
    private final T value;
    private final Event event;

    public QueryResponse(T value, Event event) {
        this.value = value;
        this.event = event;
    }

    public QueryResponse(Event event) {
        this.event = event;
        value = null;
    }

    public T getValue() {
        return value;
    }

    public Event getEvent() {
        return event;
    }
}

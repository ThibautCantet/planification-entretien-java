package com.soat.planification_entretien.cqrs;


import java.util.ArrayList;
import java.util.List;

public class CommandResponse<T, E extends Event> {

    private T value;
    List<E> events;

    public CommandResponse(E event) {
        this(null, event);
    }

    public CommandResponse(T value, E event) {
        this.value = value;
        this.events = new ArrayList<>();
        events.add(event);
    }

    public boolean containEventType(Class<? extends Event> clazz) {
        return events.stream()
                .anyMatch(e -> e.getClass().equals(clazz));
    }

    public T value() {
        return value;
    }

    public List<E> events() {
        return events;
    }
}

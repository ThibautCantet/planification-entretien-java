package com.soat.planification_entretien.infrastructure.middleware;

public interface Listener<T extends Event> {
    void onEvent(T event);
}

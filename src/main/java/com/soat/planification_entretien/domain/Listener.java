package com.soat.planification_entretien.domain;

public interface Listener<T extends Event> {
    void onMessage(T msg);
}

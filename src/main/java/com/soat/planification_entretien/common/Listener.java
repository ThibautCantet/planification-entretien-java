package com.soat.planification_entretien.common;

public interface Listener<E extends Event> {
    void onMessage(E msg);
}

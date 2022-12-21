package com.soat.planification_entretien.application_service;

import com.soat.planification_entretien.domain.Event;

public interface Listener<E extends Event> {
    void onMessage(E msg);
}

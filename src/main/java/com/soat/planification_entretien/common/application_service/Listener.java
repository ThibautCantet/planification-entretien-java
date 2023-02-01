package com.soat.planification_entretien.common.application_service;

import com.soat.planification_entretien.common.domain.Event;

public interface Listener<E extends Event> {
    void onMessage(E msg);
}

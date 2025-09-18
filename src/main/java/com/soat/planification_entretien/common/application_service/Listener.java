package com.soat.planification_entretien.common.application_service;

public interface Listener<E> {
    void onMessage(E msg);
}

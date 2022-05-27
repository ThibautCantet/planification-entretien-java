package com.soat.planification_entretien.infrastructure.middleware;

public interface Bus {
    void send(Event msg);
}

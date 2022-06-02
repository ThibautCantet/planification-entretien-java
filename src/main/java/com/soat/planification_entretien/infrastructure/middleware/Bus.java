package com.soat.planification_entretien.infrastructure.middleware;

import com.soat.planification_entretien.cqrs.Event;

public interface Bus {
    void send(Event msg);
}

package com.soat.planification_entretien.infrastructure.middleware;

import java.util.ArrayList;
import java.util.List;

import com.soat.planification_entretien.domain.rendez_vous.AjouterAuCalendrier;
import com.soat.planification_entretien.domain.entretien.ConfirmerPlanificationEntretien;
import org.springframework.stereotype.Service;

@Service
public class MessageBus implements Bus {
    private final List<Listener> subs = new ArrayList<>();

    public MessageBus(ConfirmerPlanificationEntretien confirmerPlanificationEntretien, AjouterAuCalendrier ajouterAuCalendrier) {
        subs.addAll(List.of(confirmerPlanificationEntretien, ajouterAuCalendrier));
    }

    public void send(Event event) {
        for (Listener l : subs) {
            l.onEvent(event);
        }
    }
}

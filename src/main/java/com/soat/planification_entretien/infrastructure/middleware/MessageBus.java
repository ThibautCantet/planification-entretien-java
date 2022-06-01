package com.soat.planification_entretien.infrastructure.middleware;

import java.util.ArrayList;
import java.util.List;

import com.soat.planification_entretien.domain.entretien.AjouterEntretien;
import com.soat.planification_entretien.domain.entretien.EntretienRepository;
import com.soat.planification_entretien.domain.rendez_vous.CalendrierRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageBus implements Bus {
    private final List<Listener> subs = new ArrayList<>();
    private final EntretienRepository entretienRepository;
    private final CalendrierRepository calendrierRepository;

    public MessageBus(EntretienRepository entretienRepository, CalendrierRepository calendrierRepository) {
        this.entretienRepository = entretienRepository;
        this.calendrierRepository = calendrierRepository;
        subs.addAll(List.of(new AjouterEntretien(this.entretienRepository, this.calendrierRepository)));
    }

    public void send(Event event) {
        for (Listener l : subs) {
            l.onEvent(event);
        }
    }
}

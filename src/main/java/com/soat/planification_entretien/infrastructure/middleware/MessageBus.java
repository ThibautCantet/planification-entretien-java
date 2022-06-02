package com.soat.planification_entretien.infrastructure.middleware;

import java.util.ArrayList;
import java.util.List;

import com.soat.planification_entretien.domain.entretien.AjouterEntretien;
import com.soat.planification_entretien.domain.entretien.CalendrierDAO;
import com.soat.planification_entretien.domain.entretien.EntretienRepository;
import com.soat.planification_entretien.domain.entretien.ProjectionCalendrierJson;
import com.soat.planification_entretien.domain.rendez_vous.CalendrierRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageBus implements Bus {
    private final List<Listener> subs = new ArrayList<>();
    private final EntretienRepository entretienRepository;
    private final CalendrierRepository calendrierRepository;
    private final CalendrierDAO calendrierDAO;

    public MessageBus(EntretienRepository entretienRepository, CalendrierRepository calendrierRepository, CalendrierDAO calendrierDAO) {
        this.entretienRepository = entretienRepository;
        this.calendrierRepository = calendrierRepository;
        this.calendrierDAO = calendrierDAO;
        subs.addAll(List.of(
                new AjouterEntretien(this.entretienRepository, this.calendrierRepository),
                new ProjectionCalendrierJson(this.calendrierDAO, this.entretienRepository, this.calendrierRepository)
        ));
    }

    public void send(Event event) {
        for (Listener l : subs) {
            l.onEvent(event);
        }
    }
}

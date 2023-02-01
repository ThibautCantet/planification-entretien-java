package com.soat.planification_entretien.common.application_service;

import java.util.ArrayList;
import java.util.List;

import com.soat.planification_entretien.common.domain.Event;
import org.springframework.stereotype.Service;

@Service
public class MessageBus {
    private List<Listener> subs = new ArrayList<>();

    public void subscribe(Listener l) {
        this.subs.add(l);
    }

    public void send(Event msg) {
        for (Listener l : subs) {
            l.onMessage(msg);
        }
    }
}

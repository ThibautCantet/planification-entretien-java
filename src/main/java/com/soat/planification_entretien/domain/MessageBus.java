package com.soat.planification_entretien.domain;

import java.util.ArrayList;
import java.util.List;

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

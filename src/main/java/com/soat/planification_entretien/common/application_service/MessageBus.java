package com.soat.planification_entretien.common.application_service;

import java.util.ArrayList;
import java.util.List;

import com.soat.planification_entretien.common.domain.Event;
import org.springframework.stereotype.Service;

public class MessageBus {

    private static final MessageBus INSTANCE = new MessageBus();

    public static MessageBus instance() {
        return INSTANCE;
    }

    private final List<Listener> subs = new ArrayList<>();
    private MessageBus() {
    }

    public void subscribe(Listener l) {
        this.subs.add(l);
    }

    public void send(Event msg) {
        for (Listener l : subs) {
            l.onMessage(msg);
        }
    }
}

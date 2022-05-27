package com.soat.planification_entretien.infrastructure.middleware.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.soat.planification_entretien.cqrs.Command;
import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.cqrs.EventHandler;

public class EventBusDispatcher implements EventBus {

    private final Map<Class, ? extends List<? extends EventHandler>> eventHandlers;

    public EventBusDispatcher(List<? extends EventHandler> eventHandlers) {
        this.eventHandlers = eventHandlers.stream()
                .collect(Collectors.groupingBy(EventHandler::listenTo));
    }

    @Override
    public <C extends Command> C publish(Event event) {
        List<? extends EventHandler> eventHandlers = getListeners(event);

        var commands = new ArrayList<Command>();
        for (var eventHandler : eventHandlers) {
            Event newEvent = eventHandler.handle(event);
            if (newEvent != null) {
                this.publish(newEvent);
            }
        }

        return (C) commands.stream().findFirst().orElse(null);
    }

    private List<? extends EventHandler> getListeners(Event event) {
        return this.eventHandlers.entrySet().stream()
                .filter(entry -> entry.getKey().isInstance(event))
                .flatMap(classEntry -> classEntry.getValue().stream())
                .collect(Collectors.toList());
    }
}

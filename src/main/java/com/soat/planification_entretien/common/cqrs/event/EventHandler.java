package com.soat.planification_entretien.common.cqrs.event;

public interface EventHandler<T extends Event> {
    Class<T> listenTo();

    EventHandlerType getType();
}

package com.soat.planification_entretien.common.cqrs.event;

public abstract class EventHandlerCommand<E extends Event> implements EventHandlerReturnCommand<E> {

    @Override
    public EventHandlerType getType() {
        return EventHandlerType.COMMAND;
    }
}

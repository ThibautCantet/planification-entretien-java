package com.soat.planification_entretien.cqrs;

public abstract class EventHandlerCommand<E> implements EventHandlerReturnCommand<E> {

    @Override
    public EventHandlerType getType() {
        return EventHandlerType.COMMAND;
    }
}

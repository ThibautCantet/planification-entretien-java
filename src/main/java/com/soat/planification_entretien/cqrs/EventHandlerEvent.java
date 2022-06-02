package com.soat.planification_entretien.cqrs;

public abstract class EventHandlerEvent<E> implements EventHandlerReturnEvent<E> {

    @Override
    public EventHandlerType getType() {
        return EventHandlerType.EVENT;
    }
}

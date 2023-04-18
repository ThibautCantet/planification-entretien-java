package com.soat.planification_entretien.common.cqrs.event;

public abstract class EventHandlerEvent<E extends Event> implements EventHandlerReturnEvent<E> {

    @Override
    public EventHandlerType getType() {
        return EventHandlerType.EVENT;
    }
}

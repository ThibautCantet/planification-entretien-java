package com.soat.planification_entretien.common.cqrs.event;

public abstract class EventHandlerVoid<E extends Event> implements EventHandlerReturnVoid<E> {

    @Override
    public EventHandlerType getType() {
        return EventHandlerType.VOID;
    }
}

package com.soat.planification_entretien.cqrs;

public abstract class EventHandlerVoid<E> implements EventHandlerReturnVoid<E> {

    @Override
    public EventHandlerType getType() {
        return EventHandlerType.VOID;
    }
}

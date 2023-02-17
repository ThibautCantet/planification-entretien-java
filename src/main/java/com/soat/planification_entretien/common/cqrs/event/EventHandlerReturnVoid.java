package com.soat.planification_entretien.common.cqrs.event;


public interface EventHandlerReturnVoid<E extends Event> extends EventHandler<E> {

    void handle(E event);

}

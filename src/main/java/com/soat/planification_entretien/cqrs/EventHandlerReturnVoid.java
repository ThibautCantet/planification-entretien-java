package com.soat.planification_entretien.cqrs;


public interface EventHandlerReturnVoid<E> extends EventHandler {

    void handle(E event);

}

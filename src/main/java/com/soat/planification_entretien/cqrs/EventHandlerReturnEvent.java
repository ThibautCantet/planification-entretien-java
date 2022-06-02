package com.soat.planification_entretien.cqrs;


public interface EventHandlerReturnEvent<E> extends EventHandler {

    <Ev extends Event> Ev handle(E event);

}

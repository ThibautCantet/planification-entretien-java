package com.soat.planification_entretien.cqrs;


public interface EventHandlerReturnCommand<E> extends EventHandler {

    <C extends Command> C handle(E event);

}

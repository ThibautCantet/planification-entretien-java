package com.soat.planification_entretien.common.cqrs.event;


import com.soat.planification_entretien.common.cqrs.command.Command;

public interface EventHandlerReturnCommand<E extends Event> extends EventHandler<E> {

    <C extends Command> C handle(E event);

}

package com.soat.planification_entretien.cqrs;

public interface EventHandler<T extends Event> {
    Event handle(T event);

    Class listenTo();
}

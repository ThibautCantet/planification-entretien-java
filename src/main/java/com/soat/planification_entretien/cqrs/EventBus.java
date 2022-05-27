package com.soat.planification_entretien.cqrs;

public interface EventBus {
    <C extends Command> C publish(Event event);
}

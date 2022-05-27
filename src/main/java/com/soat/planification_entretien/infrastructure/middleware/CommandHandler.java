package com.soat.planification_entretien.infrastructure.middleware;

public interface CommandHandler<T extends Command, R> {

    R handle(T command);
}

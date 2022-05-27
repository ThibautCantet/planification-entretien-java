package com.soat.planification_entretien.infrastructure.middleware.command;

import com.soat.planification_entretien.cqrs.Command;

public class UnmatchedCommandHandlerException extends RuntimeException {
    public <C extends Command> UnmatchedCommandHandlerException(C command) {

    }
}

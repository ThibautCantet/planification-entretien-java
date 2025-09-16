package com.soat.planification_entretien.common.cqrs.middleware.command;


import com.soat.planification_entretien.common.cqrs.command.Command;

public class UnmatchedCommandHandlerException extends RuntimeException {
    public <C extends Command> UnmatchedCommandHandlerException(C command) {

    }
}

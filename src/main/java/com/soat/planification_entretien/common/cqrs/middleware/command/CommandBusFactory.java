package com.soat.planification_entretien.common.cqrs.middleware.command;

import java.util.List;

import com.soat.planification_entretien.candidat.command.CreerCandidatCommandHandler;
import com.soat.planification_entretien.candidat.command.domain.CandidatRepository;
import com.soat.planification_entretien.candidat.command.domain_service.CandidatFactory;
import com.soat.planification_entretien.common.application_service.MessageBus;
import com.soat.planification_entretien.common.cqrs.command.CommandHandler;
import com.soat.planification_entretien.common.cqrs.event.Event;
import com.soat.planification_entretien.common.cqrs.event.EventHandler;
import com.soat.planification_entretien.common.cqrs.middleware.event.EventBus;
import com.soat.planification_entretien.common.cqrs.middleware.event.EventBusFactory;
import org.springframework.stereotype.Service;

@Service
public class CommandBusFactory {

    private final MessageBus messsageBus;
    private final CandidatRepository candidatRepository;
    private final CandidatFactory candidatFactory;

    public CommandBusFactory(MessageBus messsageBus, CandidatRepository candidatRepository, CandidatFactory candidatFactory) {
        this.messsageBus = messsageBus;
        this.candidatRepository = candidatRepository;
        this.candidatFactory = candidatFactory;
    }

    protected List<CommandHandler> getCommandHandlers() {
        return List.of(
                new CreerCandidatCommandHandler(candidatRepository, candidatFactory)
        );
    }

    protected List<EventHandler<? extends Event>> getEventHandlers() {
        return List.of();
    }

    public CommandBus build() {
        CommandBusDispatcher commandBusDispatcher = buildCommandBusDispatcher();

        EventBus eventBus = buildEventBus();

        CommandBusLogger commandBusLogger = new CommandBusLogger(commandBusDispatcher);

        return new EventBusDispatcherCommandBus(commandBusLogger, eventBus);
    }

    private EventBus buildEventBus() {
        EventBusFactory eventBusFactory = new EventBusFactory(getEventHandlers());
        return eventBusFactory.build();
    }

    private CommandBusDispatcher buildCommandBusDispatcher() {
        List<CommandHandler> commandHandlers = getCommandHandlers();
        return new CommandBusDispatcher(commandHandlers);
    }
}

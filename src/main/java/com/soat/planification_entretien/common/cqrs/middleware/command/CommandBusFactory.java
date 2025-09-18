package com.soat.planification_entretien.common.cqrs.middleware.command;

import java.util.List;

import com.soat.planification_entretien.candidat.command.application_service.CreerCandidatCommandHandler;
import com.soat.planification_entretien.candidat.command.domain.port.repository.CandidatRepository;
import com.soat.planification_entretien.candidat.command.domain_service.CandidatFactory;
import com.soat.planification_entretien.common.application_service.MessageBus;
import com.soat.planification_entretien.common.cqrs.command.CommandHandler;
import com.soat.planification_entretien.common.cqrs.event.Event;
import com.soat.planification_entretien.common.cqrs.event.EventHandler;
import com.soat.planification_entretien.common.cqrs.middleware.event.EventBus;
import com.soat.planification_entretien.common.cqrs.middleware.event.EventBusFactory;
import com.soat.planification_entretien.entretien.command.application_service.PlanifierEntretienCommandHandler;
import com.soat.planification_entretien.entretien.command.application_service.ValiderEntretienCommandHandler;
import com.soat.planification_entretien.entretien.command.domain.port.repository.EntretienRepository;
import com.soat.planification_entretien.entretien.query.domain.port.service.EmailService;
import com.soat.planification_entretien.recruteur.command.application_service.CreerRecruteurCommandHandler;
import com.soat.planification_entretien.recruteur.command.application_service.RendreRecruteurIndisponibleCommandHandler;
import com.soat.planification_entretien.recruteur.command.domain.port.repository.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class CommandBusFactory {

    private final CandidatRepository candidatRepository;
    private final CandidatFactory candidatFactory;
    private final EntretienRepository entretienRepository;
    private final EmailService emailService;
    private final RecruteurRepository recruteurRepository;
    private final MessageBus messageBus;

    public CommandBusFactory(CandidatRepository candidatRepository,
                             CandidatFactory candidatFactory,
                             EntretienRepository entretienRepository,
                             EmailService emailService,
                             RecruteurRepository recruteurRepository,
                             MessageBus messageBus) {
        this.candidatRepository = candidatRepository;
        this.candidatFactory = candidatFactory;
        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
        this.recruteurRepository = recruteurRepository;
        this.messageBus = messageBus;
    }

    protected List<CommandHandler> getCommandHandlers() {
        return List.of(
                new CreerCandidatCommandHandler(candidatRepository, candidatFactory),
                new PlanifierEntretienCommandHandler(entretienRepository, emailService,messageBus),
                new ValiderEntretienCommandHandler(entretienRepository),
                new CreerRecruteurCommandHandler(recruteurRepository, messageBus),
                new RendreRecruteurIndisponibleCommandHandler(recruteurRepository)
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

package com.soat.planification_entretien.common.cqrs.middleware.command;

import java.util.List;

import com.soat.planification_entretien.candidat.command.CreerCandidatCommandHandler;
import com.soat.planification_entretien.candidat.command.domain.CandidatRepository;
import com.soat.planification_entretien.candidat.command.domain_service.CandidatFactory;
import com.soat.planification_entretien.common.cqrs.command.CommandHandler;
import com.soat.planification_entretien.common.cqrs.event.Event;
import com.soat.planification_entretien.common.cqrs.event.EventHandler;
import com.soat.planification_entretien.common.cqrs.middleware.event.EventBus;
import com.soat.planification_entretien.common.cqrs.middleware.event.EventBusFactory;
import com.soat.planification_entretien.entretien.command.AnnulerEntretienCommandHandler;
import com.soat.planification_entretien.entretien.command.PlanifierEntretienCommandHandler;
import com.soat.planification_entretien.entretien.command.ValiderEntretienCommandHandler;
import com.soat.planification_entretien.entretien.command.domain.EmailService;
import com.soat.planification_entretien.entretien.command.domain.EntretienRepository;
import com.soat.planification_entretien.recruteur.command.CreerRecruteurCommandHandler;
import com.soat.planification_entretien.recruteur.command.EntretienCreeListener;
import com.soat.planification_entretien.recruteur.command.RendreRecruteurIndisponibleCommandHandler;
import com.soat.planification_entretien.recruteur.command.domain.RecruteurRepository;
import com.soat.planification_entretien.recruteur.query.application.RecruteurCréeListener;
import com.soat.planification_entretien.recruteur.query.application.RecruteurDao;
import org.springframework.stereotype.Service;

@Service
public class CommandBusFactory {

    private final EntretienRepository entretienRepository;
    private final EmailService emailService;
    private final CandidatRepository candidatRepository;
    private final CandidatFactory candidatFactory;
    private final RecruteurRepository recruteurRepository;
    private final RecruteurDao recruteurDao;

    public CommandBusFactory(EntretienRepository entretienRepository, EmailService emailService, CandidatRepository candidatRepository, CandidatFactory candidatFactory, RecruteurRepository recruteurRepository, RecruteurDao recruteurDao) {
        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
        this.candidatRepository = candidatRepository;
        this.candidatFactory = candidatFactory;
        this.recruteurRepository = recruteurRepository;
        this.recruteurDao = recruteurDao;
    }

    protected List<CommandHandler> getCommandHandlers() {
        return List.of(
                new PlanifierEntretienCommandHandler(entretienRepository, emailService),
                new CreerCandidatCommandHandler(candidatRepository, candidatFactory),
                new ValiderEntretienCommandHandler(entretienRepository),
                new AnnulerEntretienCommandHandler(entretienRepository),
                new CreerRecruteurCommandHandler(recruteurRepository)
        );
    }

    protected List<EventHandler<? extends Event>> getEventHandlers() {
        return List.of(
                new EntretienCreeListener(new RendreRecruteurIndisponibleCommandHandler(recruteurRepository)),
                new RecruteurCréeListener(recruteurDao)
        );
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

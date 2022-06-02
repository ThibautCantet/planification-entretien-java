package com.soat.planification_entretien.infrastructure.middleware.command;

import java.util.List;

import com.soat.planification_entretien.cqrs.CommandHandler;
import com.soat.planification_entretien.cqrs.EventHandler;
import com.soat.planification_entretien.domain.entretien.AjouterEntretien;
import com.soat.planification_entretien.domain.entretien.EmailService;
import com.soat.planification_entretien.domain.entretien.EntretienRepository;
import com.soat.planification_entretien.domain.entretien.EnvoyerEmails;
import com.soat.planification_entretien.domain.entretien.PlanifierEntretienCommandHandler;
import com.soat.planification_entretien.domain.recruteur.CreerRecruteurCommandHandler;
import com.soat.planification_entretien.domain.recruteur.RecruteurRepository;
import com.soat.planification_entretien.domain.rendez_vous.CalendrierRepository;
import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.infrastructure.middleware.event.EventBus;
import com.soat.planification_entretien.infrastructure.middleware.event.EventBusFactory;
import org.springframework.stereotype.Service;

@Service
public class CommandBusFactory {

    private final EntretienRepository entretienRepository;
    private final EmailService emailService;
    private final CalendrierRepository calendrierRepository;
    private final RecruteurRepository recruteurRepository;

    public CommandBusFactory(EntretienRepository entretienRepository, EmailService emailService, CalendrierRepository calendrierRepository, RecruteurRepository recruteurRepository) {
        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
        this.calendrierRepository = calendrierRepository;
        this.recruteurRepository = recruteurRepository;
    }

    protected List<CommandHandler> getCommandHandlers() {
        return List.of(
                new PlanifierEntretienCommandHandler(entretienRepository),
                new CreerRecruteurCommandHandler(recruteurRepository));
    }

    protected List<EventHandler<? extends Event>> getEventHandlers() {
        return List.of(new AjouterEntretien(entretienRepository, calendrierRepository),
                new EnvoyerEmails(emailService));
    }

    public CommandBus build() {
        CommandBusDispatcher commandBusDispatcher = buildCommandBusDispatcher();

        EventBus eventBus = buildEventBus();

        CommandBusLogger commandBusLogger = new CommandBusLogger(commandBusDispatcher);

        return new EventBusDispatcherCommandBus(commandBusDispatcher, eventBus);
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

package com.soat.planification_entretien.infrastructure.middleware.command;

import java.util.List;

import com.soat.planification_entretien.domain.entretien.ConfirmerPlanificationEntretien;
import com.soat.planification_entretien.domain.entretien.EmailService;
import com.soat.planification_entretien.domain.entretien.EntretienRepository;
import com.soat.planification_entretien.domain.entretien.PlanifierEntretienCommandHandler;
import com.soat.planification_entretien.domain.rendez_vous.AjouterAuCalendrier;
import com.soat.planification_entretien.domain.rendez_vous.CalendrierRepository;
import com.soat.planification_entretien.cqrs.CommandHandler;
import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.infrastructure.middleware.event.EventBus;
import com.soat.planification_entretien.infrastructure.middleware.event.EventBusFactory;
import com.soat.planification_entretien.cqrs.EventHandler;
import org.springframework.stereotype.Service;

@Service
public class CommandBusFactory {

    private final EntretienRepository entretienRepository;
    private final EmailService emailService;
    private final CalendrierRepository calendrierRepository;

    public CommandBusFactory(EntretienRepository entretienRepository, EmailService emailService, CalendrierRepository calendrierRepository) {
        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
        this.calendrierRepository = calendrierRepository;
    }

    protected List<CommandHandler> getCommandHandlers() {
        return List.of(new PlanifierEntretienCommandHandler(entretienRepository));
    }

    protected List<EventHandler<? extends Event>> getEventHandlers() {
        return List.of(new AjouterAuCalendrier(calendrierRepository),
                new ConfirmerPlanificationEntretien(emailService));
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

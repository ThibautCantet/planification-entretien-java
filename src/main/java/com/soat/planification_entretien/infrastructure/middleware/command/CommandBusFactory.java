package com.soat.planification_entretien.infrastructure.middleware.command;

import java.util.List;

import com.soat.planification_entretien.cqrs.CommandHandler;
import com.soat.planification_entretien.cqrs.EventHandler;
import com.soat.planification_entretien.candidat.command.repository.CandidatRepository;
import com.soat.planification_entretien.candidat.command.CreerCandidatCommandHandler;
import com.soat.planification_entretien.entretien.command.MettreAJourStatusEntretienCommandHandler;
import com.soat.planification_entretien.entretien.listener.AjouterEntretien;
import com.soat.planification_entretien.entretien.listener.ProjectionCalendrierJson;
import com.soat.planification_entretien.entretien.listener.dao.CalendrierDAO;
import com.soat.planification_entretien.entretien.listener.service.EmailService;
import com.soat.planification_entretien.entretien.command.domain.repository.EntretienRepository;
import com.soat.planification_entretien.entretien.listener.dao.EntretienDAO;
import com.soat.planification_entretien.entretien.listener.EnvoyerEmails;
import com.soat.planification_entretien.entretien.command.PlanifierEntretienCommandHandler;
import com.soat.planification_entretien.recruteur.command.CreerRecruteurCommandHandler;
import com.soat.planification_entretien.recruteur.command.domain.repository.RecruteurRepository;
import com.soat.planification_entretien.rendez_vous.command.AjouterRendezVousCommandHandler;
import com.soat.planification_entretien.rendez_vous.command.repository.CalendrierRepository;
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
    private final CandidatRepository candidatRepository;
    private final EntretienDAO entretienDAO;
    private final CalendrierDAO calendrierDao;

    public CommandBusFactory(EntretienRepository entretienRepository, EmailService emailService, CalendrierRepository calendrierRepository, RecruteurRepository recruteurRepository, CandidatRepository candidatRepository, EntretienDAO entretienDAO, CalendrierDAO calendrierDao) {
        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
        this.calendrierRepository = calendrierRepository;
        this.recruteurRepository = recruteurRepository;
        this.candidatRepository = candidatRepository;
        this.entretienDAO = entretienDAO;
        this.calendrierDao = calendrierDao;
    }

    protected List<CommandHandler> getCommandHandlers() {
        return List.of(
                new PlanifierEntretienCommandHandler(entretienRepository),
                new CreerRecruteurCommandHandler(recruteurRepository),
                new CreerCandidatCommandHandler(candidatRepository),
                new AjouterRendezVousCommandHandler(calendrierRepository),
                new MettreAJourStatusEntretienCommandHandler(entretienRepository)
        );
    }

    protected List<EventHandler<? extends Event>> getEventHandlers() {
        return List.of(new AjouterEntretien(entretienDAO),
                new EnvoyerEmails(emailService),
                new ProjectionCalendrierJson(calendrierDao, entretienRepository, calendrierRepository)
        );
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

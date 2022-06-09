package com.soat.planification_entretien.rendez_vous.command;

import java.util.ArrayList;

import com.soat.planification_entretien.cqrs.CommandHandler;
import com.soat.planification_entretien.cqrs.CommandResponse;
import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.rendez_vous.command.domain.entity.Calendrier;
import com.soat.planification_entretien.rendez_vous.command.domain.entity.RendezVous;
import com.soat.planification_entretien.rendez_vous.event.RendezAjoute;
import com.soat.planification_entretien.rendez_vous.command.repository.CalendrierRepository;

public class AjouterRendezVousCommandHandler implements CommandHandler<AjouterRendezVousCommand, CommandResponse<Integer, Event>> {
    private final CalendrierRepository calendrierRepository;

    public AjouterRendezVousCommandHandler(CalendrierRepository calendrierRepository) {
        this.calendrierRepository = calendrierRepository;
    }

    @Override
    public CommandResponse<Integer, Event> handle(AjouterRendezVousCommand command) {
        Calendrier calendrier = calendrierRepository.findByRecruteur(command.emailRecruteur())
                .orElse(new Calendrier(null, command.emailRecruteur(), new ArrayList<>()));

        RendezVous rendezVous = new RendezVous(command.emailCandidat(), command.horaire());
        calendrier.add(rendezVous);

        Integer id = calendrierRepository.save(calendrier);
        return new CommandResponse<>(id, new RendezAjoute());
    }

    @Override
    public Class listenTo() {
        return AjouterRendezVousCommand.class;
    }
}

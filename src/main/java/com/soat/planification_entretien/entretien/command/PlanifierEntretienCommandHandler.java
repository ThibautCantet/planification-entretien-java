package com.soat.planification_entretien.entretien.command;

import java.util.UUID;

import com.soat.planification_entretien.cqrs.CommandHandler;
import com.soat.planification_entretien.cqrs.CommandResponse;
import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.entretien.command.domain.entity.Entretien;
import com.soat.planification_entretien.entretien.command.domain.repository.EntretienRepository;
import com.soat.planification_entretien.entretien.event.EntretienNonPlanifie;
import com.soat.planification_entretien.entretien.event.EntretienPlanifie;

public class PlanifierEntretienCommandHandler implements CommandHandler<PlanifierEntretienCommand, CommandResponse<Event>> {
    private final EntretienRepository entretienRepository;

    public PlanifierEntretienCommandHandler(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    @Override
    public CommandResponse<Event> handle(PlanifierEntretienCommand command) {
        UUID id = entretienRepository.next();
        Entretien entretien = new Entretien(id.toString(), command.candidat(), command.recruteur());
        if (entretien.planifier(command.dateEtHeureDisponibiliteDuCandidat())) {
            entretienRepository.save(entretien);

            return new CommandResponse<>(new EntretienPlanifie(id.toString(), command.candidat().getEmail(), command.recruteur().getEmail(), command.dateEtHeureDisponibiliteDuCandidat()));
        }
        return new CommandResponse<>(new EntretienNonPlanifie());
    }

    @Override
    public Class listenTo() {
        return PlanifierEntretienCommand.class;
    }
}

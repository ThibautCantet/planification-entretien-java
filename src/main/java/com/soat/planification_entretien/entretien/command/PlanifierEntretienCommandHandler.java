package com.soat.planification_entretien.entretien.command;

import com.soat.planification_entretien.cqrs.CommandHandler;
import com.soat.planification_entretien.cqrs.CommandResponse;
import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.entretien.command.domain.entity.Entretien;
import com.soat.planification_entretien.entretien.command.domain.repository.EntretienRepository;
import com.soat.planification_entretien.entretien.event.EntretienNonPlanifie;
import com.soat.planification_entretien.entretien.event.EntretienPlanifie;

public class PlanifierEntretienCommandHandler implements CommandHandler<PlanifierEntretienCommand, CommandResponse<Boolean, Event>> {
    private final EntretienRepository entretienRepository;

    public PlanifierEntretienCommandHandler(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    @Override
    public CommandResponse<Boolean, Event> handle(PlanifierEntretienCommand command) {
        Entretien entretien = new Entretien(command.candidat(), command.recruteur());
        if (entretien.planifier(command.dateEtHeureDisponibiliteDuCandidat())) {
            int id = entretienRepository.save(entretien);

            return new CommandResponse<>(true, new EntretienPlanifie(id, command.candidat().getEmail(), command.recruteur().getEmail(), command.dateEtHeureDisponibiliteDuCandidat()));
        }
        return new CommandResponse<>(false, new EntretienNonPlanifie());
    }

    @Override
    public Class listenTo() {
        return PlanifierEntretienCommand.class;
    }
}

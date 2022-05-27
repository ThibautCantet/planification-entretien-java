package com.soat.planification_entretien.domain.entretien;

import com.soat.planification_entretien.domain.entretien.entities.Entretien;
import com.soat.planification_entretien.domain.entretien.event.EntretienNonPlanifie;
import com.soat.planification_entretien.domain.entretien.event.EntretienPlanifie;
import com.soat.planification_entretien.cqrs.CommandHandler;
import com.soat.planification_entretien.cqrs.CommandResponse;
import com.soat.planification_entretien.cqrs.Event;
import org.springframework.stereotype.Service;

@Service
public class PlanifierEntretienCommandHandler implements CommandHandler<PlanifierEntretienCommand, CommandResponse<Integer, Event>> {
    private final EntretienRepository entretienRepository;

    public PlanifierEntretienCommandHandler(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    @Override
    public CommandResponse<Integer, Event> handle(PlanifierEntretienCommand command) {
        Entretien entretien = new Entretien(command.candidat(), command.recruteur());
        if (entretien.planifier(command.dateEtHeureDisponibiliteDuCandidat(), command.dateEtHeureDisponibiliteDuRecruteur())) {
            var id = entretienRepository.save(entretien);
            return new CommandResponse<>(id, new EntretienPlanifie(entretien.getEmailCandidat(), entretien.getEmailRecruteur(), entretien.getHoraireEntretien()));
        }
        return new CommandResponse<>(new EntretienNonPlanifie());

    }

    @Override
    public Class listenTo() {
        return PlanifierEntretienCommand.class;
    }
}

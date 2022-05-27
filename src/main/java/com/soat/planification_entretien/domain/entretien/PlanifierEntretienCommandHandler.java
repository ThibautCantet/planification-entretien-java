package com.soat.planification_entretien.domain.entretien;

import com.soat.planification_entretien.infrastructure.middleware.Bus;
import com.soat.planification_entretien.infrastructure.middleware.CommandHandler;
import com.soat.planification_entretien.infrastructure.middleware.MessageBus;
import org.springframework.stereotype.Service;

@Service
public class PlanifierEntretienCommandHandler implements CommandHandler<PlanifierEntretienCommand, Boolean> {
    private final EntretienRepository entretienRepository;
    private final Bus bus;

    public PlanifierEntretienCommandHandler(EntretienRepository entretienRepository, MessageBus bus) {
        this.entretienRepository = entretienRepository;
        this.bus = bus;
    }

    @Override
    public Boolean handle(PlanifierEntretienCommand command) {
        Entretien entretien = new Entretien(command.candidat(), command.recruteur());
        if (entretien.planifier(command.dateEtHeureDisponibiliteDuCandidat(), command.dateEtHeureDisponibiliteDuRecruteur())) {
            var id = entretienRepository.save(entretien);
            bus.send(new EntretienPlanifie(id, command.candidat().getEmail(), command.recruteur().getEmail(), command.dateEtHeureDisponibiliteDuCandidat()));
            return true;
        }
        return false;

    }
}

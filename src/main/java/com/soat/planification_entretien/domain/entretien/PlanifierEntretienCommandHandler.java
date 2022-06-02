package com.soat.planification_entretien.domain.entretien;

import com.soat.planification_entretien.cqrs.CommandHandler;
import com.soat.planification_entretien.cqrs.CommandResponse;
import com.soat.planification_entretien.infrastructure.middleware.Event;
import org.springframework.stereotype.Service;

@Service
public class PlanifierEntretienCommandHandler implements CommandHandler<PlanifierEntretienCommand, CommandResponse<Boolean, Event>> {
    private final EntretienRepository entretienRepository;
    private final EmailService emailService;

    public PlanifierEntretienCommandHandler(EntretienRepository entretienRepository, EmailService emailService) {
        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
    }

    @Override
    public CommandResponse<Boolean, Event> handle(PlanifierEntretienCommand command) {
        Entretien entretien = new Entretien(command.candidat(), command.recruteur());
        if (entretien.planifier(command.dateEtHeureDisponibiliteDuCandidat(), command.dateEtHeureDisponibiliteDuRecruteur())) {
            int id = entretienRepository.save(entretien);
            emailService.envoyerUnEmailDeConfirmationAuCandidat(command.candidat().getEmail(), command.dateEtHeureDisponibiliteDuCandidat());
            emailService.envoyerUnEmailDeConfirmationAuRecruteur(command.recruteur().getEmail(), command.dateEtHeureDisponibiliteDuCandidat());

            return new CommandResponse<>(true, new EntretienPlanifie(id));
        }
        return new CommandResponse<>(false, new EntretienNonPlanifie());
    }

    @Override
    public Class listenTo() {
        return PlanifierEntretienCommand.class;
    }
}

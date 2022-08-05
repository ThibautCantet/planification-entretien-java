package com.soat.planification_entretien.entretien.command;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.soat.planification_entretien.cqrs.CommandHandler;
import com.soat.planification_entretien.cqrs.CommandResponse;
import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.entretien.command.domain.entity.DisponibilitéConsultantRecruteur;
import com.soat.planification_entretien.entretien.command.domain.entity.Entretien;
import com.soat.planification_entretien.entretien.command.domain.entity.Recruteur;
import com.soat.planification_entretien.entretien.command.domain.repository.EntretienRepository;
import com.soat.planification_entretien.entretien.command.infrastructure_service.ReferentielDeConsultantRecruteur;
import com.soat.planification_entretien.entretien.event.EntretienNonPlanifie;
import com.soat.planification_entretien.entretien.event.EntretienPlanifie;

public class PlanifierEntretienAutomatiqueCommandHandler implements CommandHandler<PlanifierEntretienAutomatiqueCommand, CommandResponse<Event>> {
    private final EntretienRepository entretienRepository;
    private final ReferentielDeConsultantRecruteur referentielDeConsultantRecruteur;

    public PlanifierEntretienAutomatiqueCommandHandler(EntretienRepository entretienRepository, ReferentielDeConsultantRecruteur referentielDeConsultantRecruteur) {
        this.entretienRepository = entretienRepository;
        this.referentielDeConsultantRecruteur = referentielDeConsultantRecruteur;
    }

    @Override
    public CommandResponse<Event> handle(PlanifierEntretienAutomatiqueCommand command) {

        var disponibilitéConsultantRecruteur = new DisponibilitéConsultantRecruteur();

        List<Recruteur> recruteurs = referentielDeConsultantRecruteur.findAll();
        Optional<Recruteur> optionalRecruteur = disponibilitéConsultantRecruteur.chercherConsultantRecruteurDisponibleParMois(recruteurs, command.candidat(), command.dateEtHeureDisponibiliteDuCandidat());
        if (optionalRecruteur.isEmpty()) {
            return new CommandResponse<>(new EntretienNonPlanifie());
        }

        UUID id = entretienRepository.next();
        Entretien entretien = new Entretien(id.toString(),
                command.candidat(),
                optionalRecruteur.get(),
                command.dateEtHeureDisponibiliteDuCandidat());
        entretienRepository.save(entretien);

        return new CommandResponse<>(new EntretienPlanifie(id.toString(), command.candidat().getEmail(), optionalRecruteur.get().getEmail(), command.dateEtHeureDisponibiliteDuCandidat()));
    }

    @Override
    public Class listenTo() {
        return PlanifierEntretienAutomatiqueCommand.class;
    }
}

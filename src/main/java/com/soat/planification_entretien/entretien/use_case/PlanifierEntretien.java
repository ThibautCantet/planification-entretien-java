package com.soat.planification_entretien.entretien.use_case;

import java.time.LocalDateTime;

import com.soat.planification_entretien.common.MessageBus;
import com.soat.planification_entretien.entretien.domain.Candidat;
import com.soat.planification_entretien.entretien.domain.CompatibilitéConsultantRecruteur;
import com.soat.planification_entretien.entretien.domain.ConsultantRecruteur;
import com.soat.planification_entretien.entretien.domain.ConsultantRecruteurRepository;
import com.soat.planification_entretien.entretien.domain.DisponibilitéConsultantRecruteur;
import com.soat.planification_entretien.entretien.domain.EmailService;
import com.soat.planification_entretien.entretien.domain.Entretien;
import com.soat.planification_entretien.entretien.domain.EntretienEvent;
import com.soat.planification_entretien.entretien.domain.EntretienPlanifié;
import com.soat.planification_entretien.entretien.domain.EntretienRepository;
import com.soat.planification_entretien.profil.domain.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class PlanifierEntretien {
    private final EntretienRepository entretienRepository;
    private final EmailService emailService;
    private final MessageBus messageBus;
    private final ConsultantRecruteurRepository consultantRecruteurRepository;

    public PlanifierEntretien(EntretienRepository entretienRepository, EmailService emailService, MessageBus messageBus, ConsultantRecruteurRepository consultantRecruteurRepository) {
        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
        this.messageBus = messageBus;
        this.consultantRecruteurRepository = consultantRecruteurRepository;
    }

    public boolean execute(Candidat candidat, LocalDateTime dateEtHeureDisponibiliteDuCandidat) {

        var consultantRecruteursDisponibles = DisponibilitéConsultantRecruteur.find(consultantRecruteurRepository.findAll());
        var consultantRecruteurCompatibles = CompatibilitéConsultantRecruteur.find(consultantRecruteursDisponibles, candidat);
        if (consultantRecruteurCompatibles.isEmpty()) {
            return false;
        }
        var consultantRecruteur = consultantRecruteurCompatibles.get(0);
        Entretien entretien = new Entretien(candidat, consultantRecruteur);

        EntretienEvent entretienEvent = entretien.planifier(dateEtHeureDisponibiliteDuCandidat);
        entretienRepository.save(entretien);
        emailService.envoyerUnEmailDeConfirmationAuCandidat(candidat.email(), dateEtHeureDisponibiliteDuCandidat);
        emailService.envoyerUnEmailDeConfirmationAuRecruteur(consultantRecruteur.email(), dateEtHeureDisponibiliteDuCandidat);
        messageBus.send(entretienEvent);
        return true;
    }

}

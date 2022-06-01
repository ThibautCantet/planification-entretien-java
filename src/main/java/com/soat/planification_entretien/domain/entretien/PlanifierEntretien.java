package com.soat.planification_entretien.domain.entretien;

import java.time.LocalDateTime;

import com.soat.planification_entretien.domain.candidat.Candidat;
import com.soat.planification_entretien.domain.recruteur.Recruteur;
import com.soat.planification_entretien.infrastructure.middleware.Bus;
import org.springframework.stereotype.Service;

@Service
public class PlanifierEntretien {
    private final EntretienRepository entretienRepository;
    private final EmailService emailService;
    private final Bus bus;

    public PlanifierEntretien(EntretienRepository entretienRepository, EmailService emailService, Bus bus) {
        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
        this.bus = bus;
    }

    public boolean execute(Candidat candidat, Recruteur recruteur, LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        Entretien entretien = new Entretien(candidat, recruteur);
        if (entretien.planifier(dateEtHeureDisponibiliteDuCandidat, dateEtHeureDisponibiliteDuRecruteur)) {
            int id = entretienRepository.save(entretien);
            emailService.envoyerUnEmailDeConfirmationAuCandidat(candidat.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            emailService.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.getEmail(), dateEtHeureDisponibiliteDuCandidat);

            bus.send(new EntretienPanifie(id));
            return true;
        }
        return false;
    }

}

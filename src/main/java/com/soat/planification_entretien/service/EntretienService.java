package com.soat.planification_entretien.service;

import com.soat.planification_entretien.model.Candidat;
import com.soat.planification_entretien.model.Disponibilite;
import com.soat.planification_entretien.model.Entretien;
import com.soat.planification_entretien.model.Recruteur;
import org.springframework.stereotype.Service;

@Service
public class EntretienService {
    private final EmailService emailService;
    private final EntretienRepository entretienRepository;

    public EntretienService(EmailService emailService, EntretienRepository entretienRepository) {
        this.emailService = emailService;
        this.entretienRepository = entretienRepository;
    }

    public void planifier(Candidat candidat, Recruteur recruteur, Disponibilite disponibiliteDuCandidat, Disponibilite disponibiliteDuRecruteur) {
        Entretien entretien = new Entretien(candidat, recruteur, disponibiliteDuCandidat);
        entretienRepository.save(entretien);
        emailService.envoyerConfirmationAuCandidat(candidat.getEmail());
        emailService.envoyerConfirmationAuRecruteur(recruteur.getEmail());
    }
}

package com.soat.planification_entretien.service;

import com.soat.planification_entretien.model.*;
import com.soat.planification_entretien.repository.CandidatRepository;
import com.soat.planification_entretien.repository.EntretienRepository;
import com.soat.planification_entretien.repository.RecruteurRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class EntretienService {
    private final CandidatRepository candidatRepository;
    private final RecruteurRepository recruteurRepository;
    private final EntretienRepository entretienRepository;
    private final EmailService emailService;

    public EntretienService(CandidatRepository candidatRepository, RecruteurRepository recruteurRepository, EntretienRepository entretienRepository, EmailService emailService) {
        this.candidatRepository = candidatRepository;
        this.recruteurRepository = recruteurRepository;
        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
    }

    public ResultatPlanificationEntretien planifier(int candidatId, int recruteurId, LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDate dateDeDisponibiliteDuRecruteur) {
        Candidat candidat = candidatRepository.findById(candidatId).get();
        Disponibilite disponibiliteDuCandidat = new Disponibilite(dateEtHeureDisponibiliteDuCandidat);
        Recruteur recruteur = recruteurRepository.findById(recruteurId).get();
        HoraireEntretien horaireEntretien = new HoraireEntretien(disponibiliteDuCandidat.horaire());

        Entretien entretien = new Entretien(candidat, recruteur);
        ResultatPlanificationEntretien resultatPlanificationEntretien = entretien.planifier(disponibiliteDuCandidat, dateDeDisponibiliteDuRecruteur);

        if (resultatPlanificationEntretien instanceof EntretienPlanifie) {
            entretienRepository.save(entretien);
            emailService.envoyerUnEmailDeConfirmationAuCandidat(candidat.getEmail(), horaireEntretien);
            emailService.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.getEmail(), horaireEntretien);
        }
        return resultatPlanificationEntretien;
    }
}

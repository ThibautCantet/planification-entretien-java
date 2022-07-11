package com.soat.planification_entretien;

public class EntretienService {
    private final EntretienRepository entretienRepository;
    private final EmailService emailService;

    public EntretienService(EntretienRepository entretienRepository, EmailService emailService) {

        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
    }

    public Boolean planifier(Candidat candidat, Recruteur recruteur) {
        Entretien entretien = new Entretien(recruteur, candidat);
        if (entretien.planifier()) {
            entretienRepository.save(entretien);

            emailService.sendEmailToCandidat(candidat.email());
            emailService.sendEmailToRH(recruteur.email());
        }
        return false;
    }
}

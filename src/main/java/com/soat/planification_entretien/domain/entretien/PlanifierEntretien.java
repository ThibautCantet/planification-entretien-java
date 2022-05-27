package com.soat.planification_entretien.domain.entretien;

import java.time.LocalDateTime;

import com.soat.planification_entretien.domain.candidat.Candidat;
import com.soat.planification_entretien.domain.recruteur.Recruteur;
import com.soat.planification_entretien.infrastructure.middleware.Bus;
import com.soat.planification_entretien.infrastructure.middleware.MessageBus;
import org.springframework.stereotype.Service;

@Service
public class PlanifierEntretien {
    private final EntretienRepository entretienRepository;
    private final Bus bus;

    public PlanifierEntretien(EntretienRepository entretienRepository, MessageBus bus) {
        this.entretienRepository = entretienRepository;
        this.bus = bus;
    }

    public boolean execute(Candidat candidat, Recruteur recruteur, LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        Entretien entretien = new Entretien(candidat, recruteur);
        if (entretien.planifier(dateEtHeureDisponibiliteDuCandidat, dateEtHeureDisponibiliteDuRecruteur)) {
            var id = entretienRepository.save(entretien);
            bus.send(new EntretienPlanifie(id, candidat.getEmail(), recruteur.getEmail(), dateEtHeureDisponibiliteDuCandidat));
            return true;
        }
        return false;
    }

}

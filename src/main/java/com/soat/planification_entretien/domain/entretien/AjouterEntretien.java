package com.soat.planification_entretien.domain.entretien;

import java.util.ArrayList;
import java.util.List;

import com.soat.planification_entretien.domain.rendez_vous.CalendrierRepository;
import com.soat.planification_entretien.domain.rendez_vous.RendezVous;
import com.soat.planification_entretien.infrastructure.middleware.Listener;
import org.springframework.stereotype.Service;

@Service
public class AjouterEntretien implements Listener<EntretienPanifie> {
    private final EntretienRepository entretienRepository;
    private final CalendrierRepository calendrierRepository;

    public AjouterEntretien(EntretienRepository entretienRepository, CalendrierRepository calendrierRepository) {
        this.entretienRepository = entretienRepository;
        this.calendrierRepository = calendrierRepository;
    }

    @Override
    public void onEvent(EntretienPanifie event) {
        Entretien entretien = entretienRepository.findById(event.id());
        Calendrier calendrier = calendrierRepository.findByRecruteur(entretien.getRecruteur().getEmail())
                .orElse(new Calendrier(null, entretien.getEmailRecruteur(), new ArrayList<>()));

        RendezVous rendezVous = new RendezVous(entretien.getEmailCandidat(), entretien.getHoraireEntretien());
        calendrier.add(rendezVous);

        calendrierRepository.save(calendrier);
    }
}
package com.soat.planification_entretien.domain.rendez_vous;

import org.springframework.stereotype.Service;

@Service
public class ListerRendezVous {
    private final CalendrierRepository calendrierRepository;

    public ListerRendezVous(CalendrierRepository calendrierRepository) {
        this.calendrierRepository = calendrierRepository;
    }

    public String execute(int recruteurId) {
        return calendrierRepository.findByRecruteurId(recruteurId);
    }
}

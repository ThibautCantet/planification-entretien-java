package com.soat.planification_entretien.domain.rendez_vous;

import java.util.Optional;

import com.soat.planification_entretien.domain.entretien.CalendrierDAO;
import org.springframework.stereotype.Service;

@Service
public class ListerRendezVousRecruteur {
    private final CalendrierDAO calendrierDAO;

    public ListerRendezVousRecruteur(CalendrierDAO calendrierDAO) {
        this.calendrierDAO = calendrierDAO;
    }

    public Optional<String> execute(String email) {
        return calendrierDAO.findByRecruteur(email);
    }
}

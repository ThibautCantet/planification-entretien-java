package com.soat.planification_entretien.domain.rendez_vous;

import java.util.Optional;

import com.soat.planification_entretien.domain.entretien.CalendrierDAO;
import org.springframework.stereotype.Service;

@Service
public class ListerRendezVousRecruteurQueryHandler {
    private final CalendrierDAO calendrierDAO;

    public ListerRendezVousRecruteurQueryHandler(CalendrierDAO calendrierDAO) {
        this.calendrierDAO = calendrierDAO;
    }

    public Optional<String> handle(ListerRendezVousRecruteurQuery query) {
        return calendrierDAO.findByRecruteur(query.email());
    }
}

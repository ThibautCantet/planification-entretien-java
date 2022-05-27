package com.soat.planification_entretien.application.controller;

import com.soat.planification_entretien.domain.rendez_vous.ListerRendezVous;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RendezVousController.PATH)
public class RendezVousController {
    public static final String PATH = "/api/rendez-vous";

    private final ListerRendezVous rendezVous;

    public RendezVousController(ListerRendezVous rendezVous) {
        this.rendezVous = rendezVous;
    }

    @GetMapping(value = "/recruteur/{recruteurId}", produces = "application/json")
    public String findByRecruteur(@PathVariable int recruteurId) {
        return rendezVous.execute(recruteurId);
    }
}

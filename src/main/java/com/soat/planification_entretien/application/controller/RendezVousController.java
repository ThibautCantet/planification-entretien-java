package com.soat.planification_entretien.application.controller;

import com.soat.planification_entretien.domain.entretien.CalendrierDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RendezVousController.PATH)
public class RendezVousController {
    public static final String PATH = "/api/rendezvous";

    private final CalendrierDAO calendrierDAO;

    public RendezVousController(CalendrierDAO calendrierDAO) {
        this.calendrierDAO = calendrierDAO;
    }

    @GetMapping(value = "/recruteur/{email}", produces = "application/json")
    public ResponseEntity<String> findByRecruteurId(@PathVariable String email) {
        var optionalCalendrier = calendrierDAO.findByRecruteur(email);
        return optionalCalendrier
                .map(calendrier -> new ResponseEntity<>(calendrier, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

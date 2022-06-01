package com.soat.planification_entretien.application.controller;

import java.util.List;
import java.util.Optional;

import com.soat.planification_entretien.domain.entretien.Calendrier;
import com.soat.planification_entretien.domain.recruteur.Recruteur;
import com.soat.planification_entretien.domain.recruteur.RecruteurRepository;
import com.soat.planification_entretien.domain.rendez_vous.CalendrierRepository;
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
    private final CalendrierRepository calendrierRepository;
    private final RecruteurRepository recruteurRepository;


    public RendezVousController(CalendrierRepository calendrierRepository, RecruteurRepository recruteurRepository) {
        this.calendrierRepository = calendrierRepository;
        this.recruteurRepository = recruteurRepository;
    }

    @GetMapping("/recruteur/{email}")
    public ResponseEntity<List<RendezVousDto>> findByRecruteurId(@PathVariable String email) {
        Optional<Recruteur> optionalRecruteur = recruteurRepository.findByEmail(email);
        if (optionalRecruteur.isPresent()) {
            Calendrier calendrier = calendrierRepository.findByRecruteur(optionalRecruteur.get().getEmail()).orElse(null);
            List<RendezVousDto> rendezVousDtos = calendrier.rendezVous().stream()
                    .map(rdv -> new RendezVousDto(rdv.emailCandidat(), rdv.horaire()))
                    .toList();

            return new ResponseEntity<>(rendezVousDtos, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}

package com.soat.planification_entretien.entretien.query.infrastructure.controller;

import java.util.List;

import com.soat.planification_entretien.entretien.query.ListerEntretiensQueryHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EntretienQueryController.PATH)
public class EntretienQueryController {
    public static final String PATH = "/api/entretien/";

    private final ListerEntretiensQueryHandler listerEntretiensQueryHandler;

    public EntretienQueryController(ListerEntretiensQueryHandler listerEntretiensQueryHandler) {
        this.listerEntretiensQueryHandler = listerEntretiensQueryHandler;
    }

    @GetMapping("/")
    public ResponseEntity<List<EntretienDetailDto>> findAll() {
        var entretiens = listerEntretiensQueryHandler.handle()
                .stream()
                .map(e -> new EntretienDetailDto(e.getId(), e.getEmailCandidat(), e.getEmailRecruteur(), e.getLanguage(), e.getHoraire(), e.getStatus()))
                .toList();
        return new ResponseEntity<>(entretiens, HttpStatus.OK);
    }
}

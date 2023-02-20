package com.soat.planification_entretien.recruteur.query.infrastructure.controller;

import java.util.List;

import com.soat.planification_entretien.common.cqrs.application.QueryController;
import com.soat.planification_entretien.common.cqrs.middleware.queries.QueryBusFactory;
import com.soat.planification_entretien.recruteur.query.ListerRecruteursExperimentesQuery;
import com.soat.planification_entretien.recruteur.query.application.RecruteurDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RecruteurQueryController.PATH)
public class RecruteurQueryController extends QueryController {
    public static final String PATH = "/api/recruteur";

    public RecruteurQueryController(QueryBusFactory queryBusFactory) {
        super(queryBusFactory);
    }

    @GetMapping("")
    public ResponseEntity<List<RecruteurDetailDto>> lister() {
        List<RecruteurDetail> recruteurs = (List<RecruteurDetail>) getQueryBus().dispatch(new ListerRecruteursExperimentesQuery())
                .value();
        var dtos = recruteurs.stream()
                .map(e -> new RecruteurDetailDto(e.id(), e.competence(), e.email()))
                .toList();

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

}

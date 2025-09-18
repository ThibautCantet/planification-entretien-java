package com.soat.planification_entretien.recruteur.query.infrastructure.controller;

import java.util.List;

import com.soat.planification_entretien.common.cqrs.application.QueryController;
import com.soat.planification_entretien.common.cqrs.middleware.queries.QueryBusFactory;
import com.soat.planification_entretien.common.cqrs.query.QueryResponse;
import com.soat.planification_entretien.recruteur.query.application_service.ListerRecruteursExperimentesQueryHandler;
import com.soat.planification_entretien.recruteur.query.domain.model.RecruteurDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RecruteurQueryController.PATH)
public class RecruteurQueryController extends QueryController {
    public static final String PATH = "/api/recruteur/";

    public RecruteurQueryController(QueryBusFactory queryBusFactory) {
        super(queryBusFactory);
    }

    @GetMapping
    public ResponseEntity<List<RecruteurDetail>> lister() {
        QueryResponse<List<RecruteurDetail>> recruteurs = getQueryBus().dispatch(new ListerRecruteursExperimentesQueryHandler.ListerRecruteursExperimentesQuery());
        return new ResponseEntity<>(recruteurs.value(), HttpStatus.OK);
    }

}

package com.soat.planification_entretien.entretien.query.infrastructure.controller;

import java.util.List;

import com.soat.planification_entretien.common.cqrs.application.QueryController;
import com.soat.planification_entretien.common.cqrs.middleware.queries.QueryBusFactory;
import com.soat.planification_entretien.common.cqrs.query.QueryResponse;
import com.soat.planification_entretien.entretien.query.application_service.ListerEntretiensQueryHandler;
import com.soat.planification_entretien.entretien.query.domain.model.EntretienInQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EntretienQueryController.PATH)
public class EntretienQueryController extends QueryController {
    public static final String PATH = "/api/entretien/";

    public EntretienQueryController(QueryBusFactory queryBusFactory) {
        super(queryBusFactory);
    }

    @GetMapping("/")
    public ResponseEntity<List<EntretienInQuery>> findAll() {
        QueryResponse<List<EntretienInQuery>> queryResponse = getQueryBus().dispatch(new ListerEntretiensQueryHandler.ListerEntretienQuery());
        return new ResponseEntity<>(queryResponse.value(), HttpStatus.OK);
    }
}

package com.soat.planification_entretien.application.controller;

import com.soat.planification_entretien.cqrs.QueryResponse;
import com.soat.planification_entretien.domain.rendez_vous.ListerRendezVousRecruteurQuery;
import com.soat.planification_entretien.domain.rendez_vous.RendezVousTrouves;
import com.soat.planification_entretien.infrastructure.controller.QueryController;
import com.soat.planification_entretien.infrastructure.middleware.queries.QueryBusFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RendezVousController.PATH)
public class RendezVousController extends QueryController {
    public static final String PATH = "/api/rendezvous";

    public RendezVousController(QueryBusFactory queryBusFactory) {
        super(queryBusFactory);
        queryBusFactory.build();
    }

    @GetMapping(value = "/recruteur/{email}", produces = "application/json")
    public ResponseEntity<String> findByRecruteurEmail(@PathVariable String email) {
        QueryResponse<String> queryResponse = getQueryBus().dispatch(new ListerRendezVousRecruteurQuery(email));
        if (queryResponse.event() instanceof RendezVousTrouves) {
            return new ResponseEntity<>(queryResponse.value(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

package com.soat.planification_entretien.recruteur.query.application.controller;

import java.util.List;

import com.soat.planification_entretien.cqrs.QueryResponse;
import com.soat.planification_entretien.recruteur.query.ListerRecruteurQuery;
import com.soat.planification_entretien.recruteur.query.dto.Recruteur;
import com.soat.planification_entretien.infrastructure.controller.QueryController;
import com.soat.planification_entretien.infrastructure.middleware.queries.QueryBusFactory;
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
        QueryResponse<List<Recruteur>> queryResponse = getQueryBus().dispatch(new ListerRecruteurQuery());
        List<RecruteurDetailDto> recruteurs = queryResponse.value().stream()
                .map(e -> new RecruteurDetailDto(e.id(), e.language(), e.experienceInYears(), e.email()))
                .toList();

        return new ResponseEntity<>(recruteurs, HttpStatus.OK);
    }

}

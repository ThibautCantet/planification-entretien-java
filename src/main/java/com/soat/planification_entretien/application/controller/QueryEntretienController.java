package com.soat.planification_entretien.application.controller;

import java.util.List;

import com.soat.planification_entretien.cqrs.QueryResponse;
import com.soat.planification_entretien.domain.entretien.IEntretien;
import com.soat.planification_entretien.domain.entretien.ListerEntretiensQueryHandler;
import com.soat.planification_entretien.domain.entretien.ListerEntretiensQuery;
import com.soat.planification_entretien.infrastructure.controller.QueryController;
import com.soat.planification_entretien.infrastructure.middleware.queries.QueryBusFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(QueryEntretienController.PATH)
public class QueryEntretienController extends QueryController {
    public static final String PATH = "/api/entretien/";

    public QueryEntretienController(QueryBusFactory queryBusFactory, ListerEntretiensQueryHandler listerEntretiensQueryHandler) {
        super(queryBusFactory);
    }

    @GetMapping("/")
    public ResponseEntity<List<EntretienDetailDto>> findAll() {
        var query = new ListerEntretiensQuery();
        QueryResponse<List<IEntretien>> queryResponse = getQueryBus().dispatch(query);
        var entretiens = queryResponse.value()
                .stream()
                .map(e -> new EntretienDetailDto(e.getId(), e.getEmailCandidat(), e.getEmailRecruteur(), e.getLanguage(), e.getHoraire()))
                .toList();
        return new ResponseEntity<>(entretiens, HttpStatus.OK);
    }

}

package com.soat.planification_entretien.entretien.query.infrastructure.controller;

import java.util.List;

import com.soat.planification_entretien.common.cqrs.application.QueryController;
import com.soat.planification_entretien.common.cqrs.middleware.queries.QueryBusFactory;
import com.soat.planification_entretien.entretien.query.ListerEntretiensQuery;
import com.soat.planification_entretien.entretien.query.application.IEntretien;
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
    public ResponseEntity<List<EntretienDetailDto>> findAll() {
        var entretiens = (List<IEntretien>) getQueryBus().dispatch(new ListerEntretiensQuery())
                .value();

        var dtos = entretiens.stream()
                .map(e -> new EntretienDetailDto(e.getId(), e.getEmailCandidat(), e.getEmailRecruteur(), e.getLanguage(), e.getHoraire(), e.getStatus()))
                .toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}

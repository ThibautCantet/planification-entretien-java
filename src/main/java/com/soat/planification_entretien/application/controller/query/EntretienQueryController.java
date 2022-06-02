package com.soat.planification_entretien.application.controller.query;

import java.util.List;

import com.soat.planification_entretien.cqrs.QueryResponse;
import com.soat.planification_entretien.domain.entretien.event.EntretiensListes;
import com.soat.planification_entretien.domain.entretien.query.dto.IEntretien;
import com.soat.planification_entretien.domain.entretien.query.ListerEntretiensQuery;
import com.soat.planification_entretien.infrastructure.controller.QueryController;
import com.soat.planification_entretien.infrastructure.middleware.queries.QueryBusFactory;
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
        queryBusFactory.build();
    }

    @GetMapping("/")
    public ResponseEntity<List<EntretienDetailDto>> findAll() {
        QueryResponse<List<IEntretien>> queryResponse = getQueryBus().dispatch(new ListerEntretiensQuery());
        if (queryResponse.event() instanceof EntretiensListes) {
            var entretiens = queryResponse.value()
                    .stream()
                    .map(e -> new EntretienDetailDto(e.getId(), e.getEmailCandidat(), e.getEmailRecruteur(), e.getLanguage(), e.getHoraire()))
                    .toList();
            return new ResponseEntity<>(entretiens, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

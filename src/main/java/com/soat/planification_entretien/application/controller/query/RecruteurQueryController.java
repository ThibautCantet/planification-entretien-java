package com.soat.planification_entretien.application.controller.query;

import java.util.List;

import com.soat.planification_entretien.cqrs.QueryResponse;
import com.soat.planification_entretien.domain.recruteur.ListerRecruteurQuery;
import com.soat.planification_entretien.domain.recruteur.Recruteur;
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
                .map(e -> new RecruteurDetailDto(e.getId(), e.getLanguage(), e.getExperienceInYears(), e.getEmail()))
                .toList();

        return new ResponseEntity<>(recruteurs, HttpStatus.OK);
    }

}

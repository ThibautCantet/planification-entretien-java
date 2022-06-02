package com.soat.planification_entretien.domain.recruteur;

import java.util.List;

import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.cqrs.QueryHandler;
import com.soat.planification_entretien.cqrs.QueryResponse;
import org.springframework.stereotype.Service;

@Service
public class ListerQueryHandler implements QueryHandler<ListerRecruteurQuery, QueryResponse<List<Recruteur>>> {
    private final RecruteurRepository recruteurRepository;

    public ListerQueryHandler(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public QueryResponse<List<Recruteur>> handle(ListerRecruteurQuery query) {
        return new QueryResponse<>(recruteurRepository.find10AnsExperience(), null);
    }

    @Override
    public Class listenTo() {
        return ListerRecruteurQuery.class;
    }
}

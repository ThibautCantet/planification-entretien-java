package com.soat.planification_entretien.recruteur.query.application_service;

import java.util.List;

import com.soat.planification_entretien.common.cqrs.query.Query;
import com.soat.planification_entretien.common.cqrs.query.QueryHandler;
import com.soat.planification_entretien.common.cqrs.query.QueryResponse;
import com.soat.planification_entretien.recruteur.query.domain.model.RecruteurDetail;
import com.soat.planification_entretien.recruteur.query.domain.port.repository.RecruteurDao;

public class ListerRecruteursExperimentesQueryHandler implements QueryHandler<ListerRecruteursExperimentesQueryHandler.ListerRecruteursExperimentesQuery, QueryResponse<List<RecruteurDetail>>> {
    private final RecruteurDao recruteurDao;

    public ListerRecruteursExperimentesQueryHandler(RecruteurDao recruteurDao) {
        this.recruteurDao = recruteurDao;
    }

    @Override
    public QueryResponse<List<RecruteurDetail>> handle(ListerRecruteursExperimentesQuery query) {
        return new QueryResponse<>(recruteurDao.find10AnsExperience(), null);
    }

    @Override
    public Class listenTo() {
        return ListerRecruteursExperimentesQuery.class;
    }

    public record ListerRecruteursExperimentesQuery() implements Query {
    }
}

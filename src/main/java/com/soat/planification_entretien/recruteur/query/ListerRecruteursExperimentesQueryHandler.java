package com.soat.planification_entretien.recruteur.query;

import java.util.List;

import com.soat.planification_entretien.common.cqrs.query.QueryHandler;
import com.soat.planification_entretien.common.cqrs.query.QueryResponse;
import com.soat.planification_entretien.recruteur.query.application.RecruteurDao;
import com.soat.planification_entretien.recruteur.query.application.RecruteurDetail;
import com.soat.planification_entretien.recruteur.query.application.RecruteurExperimentésListés;

public class ListerRecruteursExperimentesQueryHandler implements QueryHandler<ListerRecruteursExperimentesQuery, QueryResponse<List>> {
    private final RecruteurDao recruteurDao;

    public ListerRecruteursExperimentesQueryHandler(RecruteurDao recruteurDao) {
        this.recruteurDao = recruteurDao;
    }

    @Override
    public QueryResponse<List> handle(ListerRecruteursExperimentesQuery query) {
        List<RecruteurDetail> recruteurExperimentés = recruteurDao.find10AnsExperience();
        return new QueryResponse<>(recruteurExperimentés, new RecruteurExperimentésListés());
    }

    @Override
    public Class listenTo() {
        return ListerRecruteursExperimentesQuery.class;
    }
}

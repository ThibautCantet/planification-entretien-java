package com.soat.planification_entretien.entretien.query;

import com.soat.planification_entretien.common.cqrs.query.QueryHandler;
import com.soat.planification_entretien.common.cqrs.query.QueryResponse;
import com.soat.planification_entretien.entretien.query.application.EntretienProjectionDao;
import com.soat.planification_entretien.entretien.query.application.EntretiensAnnulésComptés;

public class CompterEntretiensAnnulesQueryHandler implements QueryHandler<CompterEntretiensAnnulesQuery, QueryResponse<Integer>> {

    private final EntretienProjectionDao entretienProjectionDao;

    public CompterEntretiensAnnulesQueryHandler(EntretienProjectionDao entretienProjectionDao) {
        this.entretienProjectionDao = entretienProjectionDao;
    }

    @Override
    public QueryResponse<Integer> handle(CompterEntretiensAnnulesQuery query) {
        var entretiens = entretienProjectionDao.entretiensAnnules();
        return new QueryResponse<>(entretiens, new EntretiensAnnulésComptés());
    }

    @Override
    public Class listenTo() {
        return CompterEntretiensAnnulesQuery.class;
    }
}

package com.soat.planification_entretien.entretien.query.application_service;

import java.util.List;

import com.soat.planification_entretien.common.cqrs.query.Query;
import com.soat.planification_entretien.common.cqrs.query.QueryHandler;
import com.soat.planification_entretien.common.cqrs.query.QueryResponse;
import com.soat.planification_entretien.entretien.query.domain.model.EntretienInQuery;
import com.soat.planification_entretien.entretien.query.domain.port.repository.EntretienDao;

public class ListerEntretiensQueryHandler implements QueryHandler<ListerEntretiensQueryHandler.ListerEntretienQuery, QueryResponse<List<EntretienInQuery>>> {

    private final EntretienDao entretienDao;

    public ListerEntretiensQueryHandler(EntretienDao entretienDao) {
        this.entretienDao = entretienDao;
    }

    @Override
    public QueryResponse<List<EntretienInQuery>> handle(ListerEntretienQuery query) {
        return new QueryResponse<>(entretienDao.findAll(), null);
    }

    @Override
    public Class listenTo() {
        return ListerEntretienQuery.class;
    }

    public record ListerEntretienQuery() implements Query {
    }
}

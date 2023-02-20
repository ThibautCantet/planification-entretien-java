package com.soat.planification_entretien.entretien.query;

import java.util.List;

import com.soat.planification_entretien.common.cqrs.query.QueryHandler;
import com.soat.planification_entretien.common.cqrs.query.QueryResponse;
import com.soat.planification_entretien.entretien.query.application.EntretienDao;
import com.soat.planification_entretien.entretien.query.application.EntretiensListés;
import com.soat.planification_entretien.entretien.query.application.IEntretien;

public class ListerEntretiensQueryHandler implements QueryHandler<ListerEntretiensQuery, QueryResponse<List>> {

    private final EntretienDao entretienDao;

    public ListerEntretiensQueryHandler(EntretienDao entretienDao) {
        this.entretienDao = entretienDao;
    }

    @Override
    public QueryResponse<List> handle(ListerEntretiensQuery query) {
        List<IEntretien> entretiens = entretienDao.findAll();
        return new QueryResponse<>(entretiens, new EntretiensListés());
    }

    @Override
    public Class listenTo() {
        return ListerEntretiensQuery.class;
    }
}

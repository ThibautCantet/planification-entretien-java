package com.soat.planification_entretien.common.cqrs.middleware.queries;

import java.util.List;

import com.soat.planification_entretien.common.cqrs.query.Query;
import com.soat.planification_entretien.common.cqrs.query.QueryHandler;
import com.soat.planification_entretien.entretien.query.ListerEntretiensQueryHandler;
import com.soat.planification_entretien.entretien.query.application.EntretienDao;
import org.springframework.stereotype.Service;

@Service
public class QueryBusFactory {

    private final EntretienDao entretienDao;

    public QueryBusFactory(EntretienDao entretienDao) {
        this.entretienDao = entretienDao;
    }

    protected List<QueryHandler<? extends Query, ? extends Object>> getQueryHandlers() {
        return List.of(
                new ListerEntretiensQueryHandler(entretienDao)
        );
    }

    public QueryBus build() {
        List<QueryHandler<? extends Query, ? extends Object>> queryHandlers = getQueryHandlers();
        final QueryBusDispatcher queryBusDispatcher = new QueryBusDispatcher(queryHandlers);

        return new QueryBusLogger(queryBusDispatcher);
    }
}

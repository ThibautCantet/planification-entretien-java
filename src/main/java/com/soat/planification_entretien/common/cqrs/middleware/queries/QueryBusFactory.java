package com.soat.planification_entretien.common.cqrs.middleware.queries;

import java.util.List;

import com.soat.planification_entretien.common.cqrs.query.Query;
import com.soat.planification_entretien.common.cqrs.query.QueryHandler;
import org.springframework.stereotype.Service;

@Service
public class QueryBusFactory {


    public QueryBusFactory() {

    }

    protected List<QueryHandler<? extends Query, ? extends Object>> getQueryHandlers() {
        return List.of();
    }

    public QueryBus build() {
        List<QueryHandler<? extends Query, ? extends Object>> queryHandlers = getQueryHandlers();
        final QueryBusDispatcher queryBusDispatcher = new QueryBusDispatcher(queryHandlers);

        return new QueryBusLogger(queryBusDispatcher);
    }
}

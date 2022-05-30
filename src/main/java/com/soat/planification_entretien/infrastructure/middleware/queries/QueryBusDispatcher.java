package com.soat.planification_entretien.infrastructure.middleware.queries;

import java.util.List;
import java.util.Map;

import com.soat.planification_entretien.cqrs.Query;
import com.soat.planification_entretien.cqrs.QueryHandler;
import com.soat.planification_entretien.cqrs.QueryResponse;

import static java.util.Optional.*;
import static java.util.stream.Collectors.*;

public class QueryBusDispatcher implements QueryBus {
    private final Map<Class, QueryHandler> queryHandlers;

    public QueryBusDispatcher(List<? extends QueryHandler> queryHandlers) {
        this.queryHandlers = queryHandlers.stream()
                .collect(toMap(QueryHandler::listenTo, queryHandler -> queryHandler));
    }

    public <R extends QueryResponse, C extends Query> R dispatch(C query) {
        QueryHandler<C, R> queryHandler = this.queryHandlers.get(query.getClass());
        return ofNullable(queryHandler).map(handler -> handler.handle(query)).orElseThrow(UnmatchedQueryHandlerException::new);
    }
}

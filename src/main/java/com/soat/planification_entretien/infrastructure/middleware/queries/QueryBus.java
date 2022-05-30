package com.soat.planification_entretien.infrastructure.middleware.queries;

import com.soat.planification_entretien.cqrs.Query;
import com.soat.planification_entretien.cqrs.QueryResponse;

public interface QueryBus {
    <R extends QueryResponse, C extends Query> R dispatch(C query);
}

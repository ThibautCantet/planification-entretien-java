package com.soat.planification_entretien.common.cqrs.middleware.queries;

import com.soat.planification_entretien.common.cqrs.query.Query;
import com.soat.planification_entretien.common.cqrs.query.QueryResponse;

public interface QueryBus {
    <R extends QueryResponse, C extends Query> R dispatch(C query);
}

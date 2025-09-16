package com.soat.planification_entretien.common.cqrs.query;

public interface QueryHandler<Q extends Query, R extends QueryResponse> {

    R handle(Q query);

    Class listenTo();
}

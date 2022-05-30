package com.soat.planification_entretien.domain.entretien;

import java.util.List;

import com.soat.planification_entretien.cqrs.QueryHandler;
import com.soat.planification_entretien.cqrs.QueryResponse;
import com.soat.planification_entretien.domain.entretien.event.EntretiensListes;
import org.springframework.stereotype.Service;

@Service
public class ListerEntretiensQueryHandler implements QueryHandler<ListerEntretiensQuery, QueryResponse<List<IEntretien>>> {

    private final EntretienRepository entretienRepository;

    public ListerEntretiensQueryHandler(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    @Override
    public QueryResponse<List<IEntretien>> handle(ListerEntretiensQuery query) {
        return new QueryResponse<>(entretienRepository.findAll().stream()
                .map(IEntretien.class::cast)
                .toList(), new EntretiensListes());
    }

    @Override
    public Class listenTo() {
        return ListerEntretiensQuery.class;
    }
}

package com.soat.planification_entretien.infrastructure.preparation.eventlistener;

import com.soat.planification_entretien.common.application_service.Listener;
import com.soat.planification_entretien.common.application_service.MessageBus;
import com.soat.planification_entretien.domain.planification.EntretienPlanifié;
import com.soat.planification_entretien.use_case.peparation.RendreIndisponibleRecruteur;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class EntretienPlanifiéListener implements Listener<EntretienPlanifié> {

    private final RendreIndisponibleRecruteur rendreIndisponibleRecruteur;

    public EntretienPlanifiéListener(RendreIndisponibleRecruteur rendreIndisponibleRecruteur) {
        this.rendreIndisponibleRecruteur = rendreIndisponibleRecruteur;
    }

    @PostConstruct
    void register() {
        MessageBus.instance().subscribe(this);
    }

    @Override
    public void onMessage(EntretienPlanifié event) {
        this.rendreIndisponibleRecruteur.execute(event.recruteurId());
    }
}

package com.soat.planification_entretien.recruteur.infrastructure.listener;

import com.soat.planification_entretien.common.application_service.Listener;
import com.soat.planification_entretien.common.application_service.MessageBus;
import com.soat.planification_entretien.common.domain.Event;
import com.soat.planification_entretien.entretien.domain.EntretienPlanifie;
import com.soat.planification_entretien.recruteur.use_case.RendreIndisponibleRecruteur;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class RecruteurPlanifieListener implements Listener {

    private final RendreIndisponibleRecruteur rendreIndisponibleRecruteur;

    public RecruteurPlanifieListener(RendreIndisponibleRecruteur rendreIndisponibleRecruteur) {
        this.rendreIndisponibleRecruteur = rendreIndisponibleRecruteur;
    }

    @PostConstruct
    void register() {
        MessageBus.instance().subscribe(this);
    }

    @Override
    public void onMessage(Event evenementRecruteurPlanifie) {
        if (evenementRecruteurPlanifie instanceof EntretienPlanifie entretienPlanifie) {
            rendreIndisponibleRecruteur.execute(entretienPlanifie.recruteurId());
        }
    }
}

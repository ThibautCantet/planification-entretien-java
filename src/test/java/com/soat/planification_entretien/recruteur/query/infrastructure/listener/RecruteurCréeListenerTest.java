package com.soat.planification_entretien.recruteur.query.infrastructure.listener;

import com.soat.planification_entretien.common.application_service.MessageBus;
import com.soat.planification_entretien.recruteur.command.domain.event.RecruteurCree;
import com.soat.planification_entretien.recruteur.query.domain.model.RecruteurDetail;
import com.soat.planification_entretien.recruteur.query.domain.port.repository.RecruteurDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecruteurCréeListenerTest {

    @Mock
    public MessageBus bus;

    @Mock
    public RecruteurDao dao;

    @InjectMocks
    public RecruteurCréeListener listener;

    @Test
    void should_add_RecruteurDetail_when_at_least_10_years_of_XP() {
        RecruteurCree recruteurCree = new RecruteurCree(1, "Java", 10, "recruteur@soat.fr");

        listener.onMessage(recruteurCree);

        verify(dao).addExperimente(new RecruteurDetail(recruteurCree.id(), recruteurCree.language(), recruteurCree.experiencesInYears(), recruteurCree.email()));
    }

    @Test
    void should_not_add_RecruteurDetail_when_less_than_10_years_of_XP() {
        RecruteurCree recruteurCree = new RecruteurCree(1, "Java", 9, "recruteur@soat.fr");

        listener.onMessage(recruteurCree);

        verify(dao, never()).addExperimente(any());
    }

}

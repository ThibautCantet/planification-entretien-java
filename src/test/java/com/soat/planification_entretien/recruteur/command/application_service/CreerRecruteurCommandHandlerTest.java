package com.soat.planification_entretien.recruteur.command.application_service;


import com.soat.planification_entretien.common.cqrs.command.CommandResponse;
import com.soat.planification_entretien.common.cqrs.event.Event;
import com.soat.planification_entretien.recruteur.command.domain.event.RecruteurCree;
import com.soat.planification_entretien.recruteur.command.domain.model.Recruteur;
import com.soat.planification_entretien.recruteur.command.domain.port.repository.RecruteurRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class CreerRecruteurCommandHandlerTest {

    @InjectMocks
    private CreerRecruteurCommandHandler creerRecruteurCommandHandler;

    @Mock
    private RecruteurRepository recruteurRepository;

    @Test
    @DisplayName("L'évènement RecruteurCréé est levé après la création d'un recruteur")
    void should_send_RecruteurCréé() {
        // given | arrange
        given(recruteurRepository.save(any())).willReturn(new Recruteur(1, "Java", "toto@soat.fr", 10, true    ));

        // when | act
        CommandResponse<Event> commandResponse = creerRecruteurCommandHandler.handle(new CreerRecruteurCommandHandler.CreerRecruteurCommand("Java",
                "toto@soat.fr",
                "10"));

        // then | assert
        assertThat(commandResponse.findFirst(RecruteurCree.class).get()).isEqualTo(new RecruteurCree(1, "Java", 10, "toto@soat.fr"));
    }
}

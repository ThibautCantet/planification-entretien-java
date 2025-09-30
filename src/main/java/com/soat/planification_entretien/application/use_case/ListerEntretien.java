package com.soat.planification_entretien.application.use_case;

import java.util.List;

import com.soat.planification_entretien.application.controller.EntretienDetailDto;
import com.soat.planification_entretien.application.use_case.output_port.EntretienPort;
import org.springframework.stereotype.Service;

@Service
public class ListerEntretien {
    private final EntretienPort entretienPort;

    public ListerEntretien(EntretienPort entretienPort) {
        this.entretienPort = entretienPort;
    }

    public List<EntretienDetailDto> execute() {
        return entretienPort.findAll().stream().map(entretien ->
                new EntretienDetailDto(
                        entretien.getId(),
                        entretien.getCandidat().getEmail(),
                        entretien.getRecruteur().getEmail(),
                        entretien.getRecruteur().getLanguage(),
                        entretien.getHoraireEntretien())
        ).toList();
    }
}

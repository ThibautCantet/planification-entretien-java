package com.soat.planification_entretien.application.use_case;

import java.util.List;

import com.soat.planification_entretien.application.use_case.input_port.EntretienDetail;
import com.soat.planification_entretien.application.use_case.input_port.EntretienDetailImpl;
import com.soat.planification_entretien.application.use_case.output_port.EntretienPort;
import org.springframework.stereotype.Service;

@Service
public class ListerEntretien {
    private final EntretienPort entretienPort;

    public ListerEntretien(EntretienPort entretienPort) {
        this.entretienPort = entretienPort;
    }

    public List<EntretienDetail> execute() {
        return entretienPort.findAll().stream()
                .map(entretien ->
                        new EntretienDetailImpl(
                                entretien.getId(),
                                entretien.getCandidat().getEmail(),
                                entretien.getRecruteur().getEmail(),
                                entretien.getRecruteur().getLanguage(),
                                entretien.getHoraireEntretien())
                )
                .map(EntretienDetail.class::cast)
                .toList();
    }
}

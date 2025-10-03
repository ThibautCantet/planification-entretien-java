package com.soat.planification_entretien.application.use_case;

import java.util.List;

import com.soat.planification_entretien.application.use_case.output_port.RecruteurPort;
import com.soat.planification_entretien.application.use_case.input_port.RecruteurDetail;
import org.springframework.stereotype.Service;

@Service
public class ListerRecruteursExperimentes {

    private final RecruteurPort recruteurPort;

    public ListerRecruteursExperimentes(RecruteurPort recruteurPort) {
        this.recruteurPort = recruteurPort;
    }

    public List<RecruteurDetail> execute() {
        return recruteurPort.findAll().stream()
                .filter(recruteur -> recruteur.getExperienceInYears() >= 10)
                .map(recruteur ->
                        new RecruteurDetail(
                                recruteur.getId(),
                                recruteur.getEmail(),
                                recruteur.getLanguage(),
                                recruteur.getExperienceInYears())
                )
                .toList();
    }
}

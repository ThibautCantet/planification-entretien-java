package com.soat.planification_entretien.use_case;

import java.util.List;

import com.soat.planification_entretien.domain.Entretien;
import com.soat.planification_entretien.domain.EntretienPort;
import org.springframework.stereotype.Service;

@Service
public class ListerEntretien {
    private final EntretienPort entretienPort;

    public ListerEntretien(EntretienPort entretienPort) {
        this.entretienPort = entretienPort;
    }

    public List<Entretien> execute() {
        return entretienPort.findAll();
    }
}

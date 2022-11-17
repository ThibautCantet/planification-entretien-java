package com.soat.planification_entretien.archi_hexa.domain.use_case;

import com.soat.planification_entretien.archi_hexa.domain.entity.EntretienDetail;
import com.soat.planification_entretien.archi_hexa.domain.port.EntretienPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListerEntretien {
    private final EntretienPort entretienPort;

    public ListerEntretien(EntretienPort entretienPort) {
        this.entretienPort = entretienPort;
    }

    public List<EntretienDetail> execute() {
        return entretienPort.findAll();
    }
}

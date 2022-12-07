package com.soat.planification_entretien.archi_hexa.domain.port;

import com.soat.planification_entretien.archi_hexa.domain.model.Entretien;
import com.soat.planification_entretien.archi_hexa.domain.model.EntretienDetail;

import java.util.List;

public interface EntretienPort {
    List<EntretienDetail> findAll();

    void save(Entretien entretien);
}

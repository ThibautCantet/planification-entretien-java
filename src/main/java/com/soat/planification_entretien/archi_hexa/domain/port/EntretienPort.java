package com.soat.planification_entretien.archi_hexa.domain.port;

import com.soat.planification_entretien.archi_hexa.domain.entity.Entretien;
import com.soat.planification_entretien.archi_hexa.domain.entity.EntretienDetail;

import java.util.List;

public interface EntretienPort {
    void save(Entretien entretien);

    List<EntretienDetail> findAll();
}

package com.soat.planification_entretien.domain.entretien.listener.dao;

import com.soat.planification_entretien.domain.entretien.listener.dto.Entretien;

public interface EntretienDAO {
    Entretien findById(int id);
}

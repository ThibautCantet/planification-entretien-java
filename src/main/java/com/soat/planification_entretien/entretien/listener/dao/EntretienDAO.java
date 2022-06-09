package com.soat.planification_entretien.entretien.listener.dao;

import com.soat.planification_entretien.entretien.listener.dto.Entretien;

public interface EntretienDAO {
    Entretien findById(int id);
}

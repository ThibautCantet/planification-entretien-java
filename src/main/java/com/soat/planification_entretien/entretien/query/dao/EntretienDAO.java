package com.soat.planification_entretien.entretien.query.dao;

import java.util.List;

import com.soat.planification_entretien.entretien.query.dto.IEntretien;

public interface EntretienDAO {
    List<IEntretien> findAll();
}

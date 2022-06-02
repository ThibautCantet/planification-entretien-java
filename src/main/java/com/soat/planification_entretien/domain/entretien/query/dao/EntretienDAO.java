package com.soat.planification_entretien.domain.entretien.query.dao;

import java.util.List;

import com.soat.planification_entretien.domain.entretien.query.dto.IEntretien;

public interface EntretienDAO {
    List<IEntretien> findAll();
}

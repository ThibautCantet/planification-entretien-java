package com.soat.planification_entretien.entretien.query.application_service;

import java.util.List;

import com.soat.planification_entretien.entretien.query.domain.model.Entretien;
import com.soat.planification_entretien.entretien.query.domain.port.repository.EntretienDao;
import org.springframework.stereotype.Service;

@Service
public class ListerEntretiensQueryHandler {

    private final EntretienDao entretienDao;

    public ListerEntretiensQueryHandler(EntretienDao entretienDao) {
        this.entretienDao = entretienDao;
    }

    public List<Entretien> handle() {
        return entretienDao.findAll().stream()
                .toList();
    }

}

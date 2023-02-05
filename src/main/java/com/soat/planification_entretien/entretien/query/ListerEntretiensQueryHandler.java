package com.soat.planification_entretien.entretien.query;

import java.util.List;

import com.soat.planification_entretien.entretien.query.application.EntretienDao;
import com.soat.planification_entretien.entretien.query.application.IEntretien;
import org.springframework.stereotype.Service;

@Service
public class ListerEntretiensQueryHandler {

    private final EntretienDao entretienDao;

    public ListerEntretiensQueryHandler(EntretienDao entretienDao) {
        this.entretienDao = entretienDao;
    }

    public List<IEntretien> handle() {
        return entretienDao.findAll();
    }

}

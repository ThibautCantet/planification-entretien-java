package com.soat.planification_entretien;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntretienRepository {

    private List<Entretien> entretiens = new ArrayList<>();

    public List<Entretien> findAll() {
        return entretiens;
    }

    public void save(Entretien entretien) {
        entretiens.add(entretien);
    }
}

package com.soat.planification_entretien.recruteur.query.application;

import java.util.List;

public interface RecruteurDao {

    List<RecruteurDetail> find10AnsExperience();

    void addExperimente(RecruteurDetail recruteurDetail);
}

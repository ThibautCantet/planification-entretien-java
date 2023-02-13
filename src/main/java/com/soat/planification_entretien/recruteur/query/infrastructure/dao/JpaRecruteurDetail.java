package com.soat.planification_entretien.recruteur.query.infrastructure.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class JpaRecruteurDetail {
    @Id
    private Integer id;

    @Column
    private String competence;
    @Column
    private String email;

    public JpaRecruteurDetail(Integer id, String competence, String email) {
        this.id = id;
        this.competence = competence;
        this.email = email;
    }

    public JpaRecruteurDetail() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

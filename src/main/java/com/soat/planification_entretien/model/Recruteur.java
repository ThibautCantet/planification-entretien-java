package com.soat.planification_entretien.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Recruteur {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String language;
    @Column
    private String email;
    @Column
    private Integer experienceInYears;

    public Recruteur(String language, String email, Integer experienceInYears) {
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears;
    }

    public Recruteur() {

    }

    public String getEmail() {
        return email;
    }
}

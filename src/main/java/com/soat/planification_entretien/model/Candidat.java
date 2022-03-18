package com.soat.planification_entretien.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Candidat {
    @Id
    @GeneratedValue
    private int id;

    @Column
    private String email;
    @Column
    private int nombreAnneesXP;
    @Column
    private String technologie;

    public Candidat() {
    }

    public Candidat(String email, int nombreAnneesXP, String technologie) {
        this.email = email;
        this.nombreAnneesXP = nombreAnneesXP;
        this.technologie = technologie;
    }

    public String getEmail() {
        return email;
    }

    public int getNombreAnneesXP() {
        return nombreAnneesXP;
    }
}

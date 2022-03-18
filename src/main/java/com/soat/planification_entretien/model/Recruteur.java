package com.soat.planification_entretien.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Recruteur {
    @Id
    @GeneratedValue
    private int id;

    @Column
    private String email;
    @Column
    private int nombreAnneesXP;
    @Column
    private String technologie;

    public Recruteur() {
    }

    public Recruteur(String email, int nombreAnneesXP, String technologie) {
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

    public String getTechnologie() {
        return technologie;
    }

    public boolean peutEvaluer(Candidat candidat) {
        boolean recruteurEstPlusExperimente = candidat.getNombreAnneesXP() < getNombreAnneesXP();
        boolean recruteurEtCandidatOntLaMemeTechno = candidat.getTechnologie().equals(getTechnologie());
        return recruteurEstPlusExperimente && recruteurEtCandidatOntLaMemeTechno;
    }
}

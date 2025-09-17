package com.soat.planification_entretien.recruteur.query.infrastructure.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "recruteur")
public class HibernateRecruteurRead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String language;
    @Column
    private String email;
    @Column
    private Integer experienceInYears;

    @Column
    private Boolean disponible;

    public HibernateRecruteurRead(String language, String email, int experienceInYears) {
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears;
        this.disponible = true;
    }

    public HibernateRecruteurRead() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public String getEmail() {
        return email;
    }

    public Integer getExperienceInYears() {
        return experienceInYears;
    }


    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }
}

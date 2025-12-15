package com.soat.planification_entretien.infrastructure.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class JpaCandidat {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String language;
    @Column
    private String email;
    @Column
    private Integer experienceInYears;

    public JpaCandidat(Integer id, String language, String email, int experienceInYears) {
        this.id = id;
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears;
    }

    public JpaCandidat(String language, String email, int experienceInYears) {
        this(null, language, email, experienceInYears);
    }

    public JpaCandidat() {

    }

    public Integer getId() {
        return id;
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
}

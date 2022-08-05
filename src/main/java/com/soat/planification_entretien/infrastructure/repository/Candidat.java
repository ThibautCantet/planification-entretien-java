package com.soat.planification_entretien.infrastructure.repository;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Candidat {
    @Id
    private UUID id;

    @Column
    private String language;
    @Column
    private String email;
    @Column
    private Integer experienceInYears;

    public Candidat(UUID id, String language, String email, int experienceInYears) {
        this.id = id;
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears;
    }

    public Candidat() {

    }

    public UUID getId() {
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

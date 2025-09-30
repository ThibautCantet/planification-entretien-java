package com.soat.planification_entretien.infrastructure.repository;

import com.soat.planification_entretien.domain.model.Recruteur;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class JpaRecruteur {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String language;
    @Column
    private String email;
    @Column
    private Integer experienceInYears;

    public JpaRecruteur(String language, String email, int experienceInYears) {
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears;
    }

    public JpaRecruteur() {

    }

    public static JpaRecruteur fromDomain(Recruteur recruteur) {
        return new JpaRecruteur(recruteur.getLanguage(), recruteur.getEmail(), recruteur.getExperienceInYears());
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

    public Recruteur toDomain() {
        return new Recruteur(language, email, experienceInYears);
    }
}

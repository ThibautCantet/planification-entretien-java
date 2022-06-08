package com.soat.planification_entretien.application.controller.query;

import java.util.Objects;

public class RecruteurDetailDto {
    private String id;
    private String competence;
    private String email;

    public RecruteurDetailDto() {
    }

    public RecruteurDetailDto(String id, String language, Integer experienceInYears, String email) {
        this.id = id;
        this.competence = String.format("%s %s ans XP", language, experienceInYears);
        this.email = email;
    }

    public RecruteurDetailDto(String id, String competence, String email) {
        this.id = id;
        this.competence = competence;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getCompetence() {
        return competence;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecruteurDetailDto that = (RecruteurDetailDto) o;
        return id == that.id && Objects.equals(competence, that.competence) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, competence, email);
    }
}

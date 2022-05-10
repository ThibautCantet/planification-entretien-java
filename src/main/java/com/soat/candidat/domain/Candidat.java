package com.soat.candidat.domain;

public class Candidat {
    private Integer id;
    private String language;
    private String email;
    private Integer experienceInYears;

    private Candidat(Integer id, String language, String email, int experienceInYears) {
        this.id = id;
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears;
    }

    private Candidat(String language, String email, int experienceInYears) {
        this(null, language, email, experienceInYears);
    }

    public static Candidat of(int id, String language, String email, int experienceInYears) {
        return new Candidat(id, language, email, experienceInYears);
    }

    public static Candidat of(String language, String email, int experienceInYears) {
        return new Candidat(language, email, experienceInYears);
    }


    public static Candidat of(int id, Candidat candidat) {
        return new Candidat(id, candidat.getLanguage(), candidat.getEmail(), candidat.getExperienceInYears());
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

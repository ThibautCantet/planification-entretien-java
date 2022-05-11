package com.soat.recruteur.domain;

public class Recruteur {
    private Integer id;

    private String language;
    private String email;
    private Integer experienceInYears;

    private Recruteur(Integer id, String language, String email, int experienceInYears) {
        this.id = id;
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears;
    }

    private Recruteur(String language, String email, int experienceInYears) {
        this(null, language, email, experienceInYears);
    }

    public static Recruteur of(String language, String email, Integer experienceInYears) {
        return new Recruteur(language, email, experienceInYears);
    }

    public static Recruteur of(Integer id, String language, String email, Integer experienceInYears) {
        return new Recruteur(id, language, email, experienceInYears);
    }

    public static Recruteur of(Integer id, Recruteur recruteur) {
        return new Recruteur(id, recruteur.getLanguage(), recruteur.getEmail(), recruteur.getExperienceInYears());
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

package com.soat.planification_entretien.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Recruteur {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
    private Integer id;
    private String language;
    private String email;
    private Integer experienceInYears;

    public Recruteur(Integer id, String language, String email, int experienceInYears) {
        this.id = id;
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears;
    }
    public Recruteur(String language, String email, int experienceInYears) {
        this(null, language, email, experienceInYears);
    }

    public Recruteur() {
    }

    public static Recruteur of(String language, String email, String experienceEnAnnees) {
        if (language.isBlank() || !isEmail(email) || experienceEnAnnees.isBlank() || Integer.parseInt(experienceEnAnnees) < 0) {
            return null;
        }

        return new Recruteur(language, email, Integer.parseInt(experienceEnAnnees));
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
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

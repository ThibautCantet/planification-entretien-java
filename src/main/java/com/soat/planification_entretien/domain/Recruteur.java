package com.soat.planification_entretien.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Recruteur {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
    private static final int MINIMUM_XP_REQUISE = 3;

    private Integer id;

    private String language;
    private String email;
    private Integer experienceInYears;

    public Recruteur(String language, String email, int experienceInYears) {
        this(null, language, email, experienceInYears);
    }

    public Recruteur(Integer recruteurId, String language, String email, Integer experienceInYears) {
        if (language.isBlank() || !isEmail(email) || !email.endsWith("soat.fr") || experienceInYears < MINIMUM_XP_REQUISE) {
            throw new IllegalArgumentException();
        }
        this.id = recruteurId;
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears;
    }

    public static Recruteur of(Integer id, Recruteur recruteur) {
        recruteur.id = id;
        return recruteur;
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

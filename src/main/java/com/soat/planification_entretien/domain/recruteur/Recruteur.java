package com.soat.planification_entretien.domain.recruteur;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Recruteur {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    private Integer id;

    private String language;
    private String email;
    private Integer experienceInYears;

    public Recruteur(String language, String email, int experienceInYears) {
        this(null, language, email, experienceInYears);
    }

    public Recruteur(Integer recruteurId, String language, String email, Integer experienceInYears) {
        if (language.isBlank() || !isEmail(email) || experienceInYears < 0) {
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

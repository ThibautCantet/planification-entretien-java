package com.soat.planification_entretien.domain.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Candidat {

    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    private Integer id;

    private String language;
    private String email;
    private Integer experienceInYears;

    public Candidat(String language, String email, int experienceEnAnnees) {
        if (language.isBlank() || !isEmail(email) || experienceEnAnnees < 0) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceEnAnnees;
    }

    public Candidat(Integer id, String language, String email, Integer experienceInYears) {
        this.id = id;
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears;
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

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }
}

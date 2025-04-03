package com.soat.planification_entretien.profil.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Prospect {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    private Integer id;

    private String language;
    private String email;
    private Integer experienceInYears;

    public Prospect(String language, String email, int experienceInYears) {
        this(null, language, email, experienceInYears);
    }

    public Prospect(Integer candidatId, String language, String email, Integer experienceInYears) {
        if (language.isBlank() || !isEmail(email) || email.endsWith("soat.fr") || experienceInYears < 0) {
            throw new IllegalArgumentException();
        }
        this.id = candidatId;
        this.language = language;
        this.experienceInYears = experienceInYears;
        this.email = email;
    }

    public static Prospect of(Integer id, Prospect prospect) {
        prospect.id = id;
        return prospect;
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

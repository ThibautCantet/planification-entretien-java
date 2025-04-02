package com.soat.planification_entretien.profil.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Candidat {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    private Integer id;

    private String email;
    private Profil profil;

    public Candidat(String language, String email, int experienceInYears) {
        this(null, language, email, experienceInYears);
    }

    public Candidat(Integer candidatId, String language, String email, Integer experienceInYears) {
        if (language.isBlank() || !isEmail(email) || email.endsWith("soat.fr") || experienceInYears < 0) {
            throw new IllegalArgumentException();
        }
        this.id = candidatId;
        this.profil = new Profil(experienceInYears, language);
        this.email = email;
    }

    public static Candidat of(Integer id, Candidat candidat) {
        candidat.id = id;
        return candidat;
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
        return profil.language();
    }

    public String getEmail() {
        return email;
    }

    public Integer getExperienceInYears() {
        return profil.experienceInYears();
    }

    Profil profil() {
        return profil;
    }
}

package com.soat.planification_entretien.profil.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Recruteur {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
    private static final int MINIMUM_XP_REQUISE = 3;

    private Integer id;

    private String language;
    private String email;
    private Integer experienceInYears;
    private boolean disponible = true;

    public Recruteur(String language, String email, int experienceInYears) {
        this(null, language, email, experienceInYears, true);
    }

    public Recruteur(Integer recruteurId, String language, String email, Integer experienceInYears, boolean disponible) {
        this.disponible = disponible;
        if (language.isBlank() || !isEmail(email) || !email.endsWith("soat.fr") || experienceInYears < MINIMUM_XP_REQUISE) {
            throw new IllegalArgumentException();
        }
        this.id = recruteurId;
        this.language = language;
        this.experienceInYears = experienceInYears;
        this.email = email;
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

    public boolean isDisponible() {
        return disponible;
    }
}

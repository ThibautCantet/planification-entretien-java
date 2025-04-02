package com.soat.planification_entretien.profil.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Recruteur {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
    private static final int MINIMUM_XP_REQUISE = 3;

    private Integer id;

    private String email;
    private Profil profil;

    public Recruteur(String language, String email, int experienceInYears) {
        this(null, language, email, experienceInYears);
    }

    public Recruteur(Integer recruteurId, String language, String email, Integer experienceInYears) {
        if (language.isBlank() || !isEmail(email) || !email.endsWith("soat.fr") || experienceInYears < MINIMUM_XP_REQUISE) {
            throw new IllegalArgumentException();
        }
        this.id = recruteurId;
        this.email = email;
        this.profil = new Profil(experienceInYears, language);
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
        return profil.language();
    }

    public String getEmail() {
        return email;
    }

    public Integer getExperienceInYears() {
        return profil.experienceInYears();
    }

    public boolean estCompatible(Candidat candidat) {
        return this.profil.estCompatibe(candidat.profil());
    }
}

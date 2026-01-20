package com.soat.planification_entretien.candidat.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Candidat {

    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    private Integer id;

    private final Competence competence;
    private String email;

    public Candidat(String language, String email, int experienceInYears) {
        this(null, language, email, experienceInYears);
    }

    public Candidat(Integer candidatId, String language, String email, Integer experienceEnAnnees) {
        this.id = candidatId;
        if (!isEmail(email)) {
            throw new IllegalArgumentException();
        }
        this.competence = Competence.of(language, experienceEnAnnees);
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
        return competence.langage();
    }

    public String getEmail() {
        return email;
    }

    public Integer getExperienceInYears() {
        return competence.experienceEnAnnees();
    }
}

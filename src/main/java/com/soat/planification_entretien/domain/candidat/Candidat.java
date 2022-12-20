package com.soat.planification_entretien.domain.candidat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.soat.planification_entretien.use_case.CreerCandidat.*;

public class Candidat {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    private Integer id;

    private String language;
    private String email;
    private Integer experienceInYears;

    public Candidat(String language, String email, int experienceInYears) {
        if (language.isBlank() || !isEmail(email) || experienceInYears < 0) {
            throw new IllegalArgumentException();
        }
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears;
    }

    public Candidat(int candidatId, String language, String email, Integer experienceInYears) {
        id = candidatId;
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears;
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
        return language;
    }

    public String getEmail() {
        return email;
    }

    public Integer getExperienceInYears() {
        return experienceInYears;
    }
}

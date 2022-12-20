package com.soat.planification_entretien.domain.candidat;

public class Candidat {

    private Integer id;
    private String language;
    private CandidatEmail email;
    private Experience experience;

    public Candidat(String language, String email, int experienceInYears) {
        this(null, language, email, experienceInYears);
    }

    public Candidat(Integer candidatId, String language, String email, Integer experienceInYears) {
        if (language.isBlank() || experienceInYears < 0) {
            throw new IllegalArgumentException();
        }
        this.id = candidatId;
        this.language = language;
        this.email = new CandidatEmail(email);
        this.experience = new Experience(experienceInYears);
    }

    public static Candidat of(Integer id, Candidat candidat) {
        candidat.id = id;
        return candidat;
    }

    public Integer getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public String getAdresseEmail() {
        return email.adresse();
    }

    public Integer getExperienceInYears() {
        return experience.annee();
    }
}

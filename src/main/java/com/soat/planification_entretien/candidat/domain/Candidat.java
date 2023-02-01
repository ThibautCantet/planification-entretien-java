package com.soat.planification_entretien.candidat.domain;

public class Candidat {

    private CandidatId id;
    private Langage language;
    private CandidatEmail email;
    private Experience experience;

    public Candidat(Integer candidatId, String language, String email, Integer experienceInYears) {
        this.id = new CandidatId(candidatId);
        this.language = new Langage(language);
        this.email = new CandidatEmail(email);
        this.experience = new Experience(experienceInYears);
    }

    public static Candidat of(Integer id, Candidat candidat) {
        candidat.id = new CandidatId(id);
        return candidat;
    }

    public Integer getId() {
        return id.value();
    }

    public String getLanguage() {
        return language.nom();
    }

    public String getAdresseEmail() {
        return email.adresse();
    }

    public Integer getExperienceInYears() {
        return experience.annee();
    }
}

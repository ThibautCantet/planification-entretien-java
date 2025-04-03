package com.soat.planification_entretien.profil.domain;

public class Prospect {

    private ProspectId id;

    private final String language;
    private final EmailProspect email;
    private final Integer experienceInYears;

    public Prospect(String language, String email, int experienceInYears) {
        this(null, language, email, experienceInYears);
    }

    public Prospect(Integer prospectId, String language, String email, Integer experienceInYears) {
        if (language.isBlank() || experienceInYears < 0) {
            throw new IllegalArgumentException();
        }
        this.id = new ProspectId(prospectId);
        this.language = language;
        this.experienceInYears = experienceInYears;
        this.email = new EmailProspect(email);
    }

    public static Prospect of(Integer id, Prospect prospect) {
        prospect.id = new ProspectId(id);
        return prospect;
    }

    public Integer getId() {
        return id.value();
    }

    public String getLanguage() {
        return language;
    }

    public String getEmail() {
        return email.addresse();
    }

    public Integer getExperienceInYears() {
        return experienceInYears;
    }

}

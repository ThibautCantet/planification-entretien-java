package com.soat.candidat.domain;

record AnneeExperience(Integer experienceInYears) {
    public AnneeExperience {
        if (experienceInYears == null) {
            throw new InvalidAnneeExperience();
        }
        if (experienceInYears < 0) {
            throw new InvalidAnneeExperience();
        }
    }
}

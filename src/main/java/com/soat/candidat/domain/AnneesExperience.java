package com.soat.candidat.domain;

record AnneesExperience(Integer annees) {
    public AnneesExperience {
        if (annees == null) {
            throw new InvalidAnneesExperience();
        }
        if (annees < 0) {
            throw new InvalidAnneesExperience();
        }
    }
}

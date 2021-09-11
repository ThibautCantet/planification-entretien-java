package com.soat.candidat.domain;

record AnneesExperience(Integer nombre) {
    public AnneesExperience {
        if (nombre == null) {
            throw new InvalidAnneesExperience();
        }
        if (nombre < 0) {
            throw new InvalidAnneesExperience();
        }
    }
}

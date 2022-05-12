package com.soat.candidat.domain;

record Language(String name) {
    public Language {
        if (name.isBlank()) {
            throw new InvalidLanguage(name);
        }
    }
}

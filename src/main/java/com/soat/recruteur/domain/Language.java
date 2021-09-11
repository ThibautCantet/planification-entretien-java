package com.soat.recruteur.domain;

record Language(String language) {
    public Language {
        if (language.isBlank()) {
            throw new InvalidLanguage(language);
        }
    }
}

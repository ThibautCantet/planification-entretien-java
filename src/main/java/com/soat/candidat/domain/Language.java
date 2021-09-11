package com.soat.candidat.domain;

record Language(String language) {
    public Language {
        if (language.isBlank()) {
            throw new InvalidLanguage(language);
        }
    }
}

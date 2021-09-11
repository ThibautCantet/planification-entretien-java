package com.soat.recruteur.domain;

record Language(String name) {
    public Language {
        if (name.isBlank()) {
            throw new InvalidLanguage(name);
        }
    }
}

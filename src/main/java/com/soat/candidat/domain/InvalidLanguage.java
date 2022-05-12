package com.soat.candidat.domain;

public class InvalidLanguage extends RuntimeException {
    private final String language;

    public InvalidLanguage(String language) {
        this.language = language;
    }
}

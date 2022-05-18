package com.soat.recruteur.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

record Email(String adresse) {

    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    public Email {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        if (!m.matches()) {
            throw new InvalidEmail();
        }
    }
}

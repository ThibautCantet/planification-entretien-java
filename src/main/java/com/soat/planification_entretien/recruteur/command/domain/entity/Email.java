package com.soat.planification_entretien.recruteur.command.domain.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

record Email(String address) {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }

    static Email create(String email) {
        if (isEmail(email)) {
            return new Email(email);
        }
        return null;
    }
}

package com.soat.planification_entretien;

public interface EmailService {
    void sendEmailToCandidat(String emailCandidat);

    void sendEmailToRH(String emailRH);
}

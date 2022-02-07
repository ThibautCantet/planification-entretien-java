package com.soat.planification_entretien.service;

public interface EmailService {
    void sendToCandidat(String email);

    void sendToRecruteur(String email);
}

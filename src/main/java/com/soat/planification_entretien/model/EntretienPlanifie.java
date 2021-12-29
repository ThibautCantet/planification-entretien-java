package com.soat.planification_entretien.model;

public record EntretienPlanifie(Candidat candidat, Recruteur recruteur, HoraireEntretien horaireEntretien) implements ResultatPlanificationEntretien {
}

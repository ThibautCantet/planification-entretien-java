package com.soat.planification_entretien.model;

public record EntretienEchouee(Candidat candidat, Recruteur recruteur, HoraireEntretien horaireEntretien) implements ResultatPlanificationEntretien {
}

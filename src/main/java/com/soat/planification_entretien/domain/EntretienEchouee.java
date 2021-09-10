package com.soat.planification_entretien.domain;

public record EntretienEchouee(Candidat candidat, Recruteur recruteur, HoraireEntretien horaireEntretien) implements ResultatPlanificationEntretien {
}

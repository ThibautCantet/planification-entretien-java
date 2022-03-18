# language: fr
Fonctionnalité: Planifier un entretien de recrutement chez Soat

  Scénario: Recruteur peut tester le candidat
    Etant donné un candidat avec comme email "candidat@mail.com" et avec 3 années d’XP, faisant du "Java" et disponible le "14/03/2022" à "14:00"
    Et un recruteur avec comme email "recruteur@soat.fr" et 5 années d’XP, faisant du "Java" et disponible le "14/03/2022" à "14:00"
    Quand je planifie l’entretien
    Alors le rdv est pris (enregistré dans la base de données)
    Et un email a été envoyé au candidat
    Et un email a été envoyé au recruteur

  Scénario: Cas non passant 1 : le recruteur est plus junior que le candidat
    Etant donné un candidat avec comme email "candidat@mail.com" et avec 5 années d’XP, faisant du "Java" et disponible le "14/03/2022" à "14:00"
    Et un recruteur avec comme email "recruteur@soat.fr" et 5 années d’XP, faisant du "Java" et disponible le "14/03/2022" à "14:00"
    Quand je planifie l’entretien
    Alors le rdv n’est pas pris
    Et aucun un email est envoyé au candidat
    Et aucun email est envoyé au recruteur

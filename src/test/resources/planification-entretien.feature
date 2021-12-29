# language: fr
Fonctionnalité: Planifier un entretien de recrutement chez Soat

  Scénario: Recruteur peut tester le candidat
    Etant donné que candidat avec 3 années d’XP, faisant du "Java" et disponible le "14/03/2022" à "14h00"
    Et un recruteur 5 années d’XP, faisant du Java et disponible le "14/03/2022" à "14h00"
    Quand je planifie l’entretien
    Alors le rdv est pris (enregistré dans la base de données)
    Et un email a été envoyé au candidat
    Et un email a été envoyé au recruteur

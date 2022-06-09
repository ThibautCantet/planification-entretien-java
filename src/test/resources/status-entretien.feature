# language: fr
Fonctionnalité: Gestion des status d'un entretien

  Scénario: Confirmer un entretien
    Etant donné les recruteurs existants
      | id | email             | language | xp |
      | 1  | recruteur@soat.fr | Java     | 10 |
    Et les candidats existants
      | id | email             | language | xp |
      | 1  | candidat@mail.com | Java     | 5  |
    Et les entretiens existants
      | id | recruteur | candidat | horaire          |
      | 1  | 1         | 1        | 16/04/2019 15:00 |
    Quand on confirme l'entretien "1"
    Alors son status est modifié
    Et on récupères les entretiens avec leur statut suivants
      | id | recruteur         | candidat          | language | horaire          | status   |
      | 1  | recruteur@soat.fr | candidat@mail.com | Java     | 16/04/2019 15:00 | CONFIRME |

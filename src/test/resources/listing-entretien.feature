# language: fr
Fonctionnalité: Lister les entretiens déjà planifiés

  Scénario: Lister les entretiens déjà planifiés
    Etant donné les recruteurs existants
      | id                                   | email             | language | xp |
      | 123e4567-e89b-42d3-a456-556642440001 | recruteur@soat.fr | Java     | 10 |
    Et les candidats existants
      | id                                   | email             | language | xp |
      | 423e4567-e89b-42d3-a456-556642440001 | candidat@mail.com | Java     | 5  |
    Et les entretiens existants
      | id | recruteur | candidat | horaire          |
      | 1  | 1         | 1        | 16/04/2019 15:00 |
    Quand on liste les tous les entretiens
    Alors on récupères les entretiens suivants
      | id | recruteur         | candidat          | language | horaire          |
      | 1  | recruteur@soat.fr | candidat@mail.com | Java     | 16/04/2019 15:00 |

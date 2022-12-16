# language: fr
Fonctionnalité: Listing des recruteurs expérimentés

  Scénario: Lister les recruteurs de plus de 10 ans d'XP
    Etant donné les recruteurs existants
      | id | email               | language | xp |
      | 1  | recruteur10@soat.fr | Java     | 10 |
      | 2  | recruteur11@soat.fr | Java     | 11 |
      | 3  | recruteur9@soat.fr  | Java     | 9  |
    Quand on liste les tous les recruteurs de plus de 10 ans d'XP
    Alors on récupères les recruteurs suivants
      | id | email               | competence     |
      | 1  | recruteur10@soat.fr | Java 10 ans XP |
      | 2  | recruteur11@soat.fr | Java 11 ans XP |

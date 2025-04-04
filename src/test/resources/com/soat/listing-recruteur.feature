# language: fr
Fonctionnalité: Listing des recruteurs expérimentés

  Scénario: Lister les recruteurs de plus de 10 ans d'XP
    Etant donné les recruteurs existants
      | id | email               | language | xp | disponible |
      | 1  | recruteur10@soat.fr | Java     | 10 | true       |
      | 2  | recruteur11@soat.fr | Java     | 11 | true       |
      | 3  | recruteur9@soat.fr  | Java     | 9  | true       |
    Quand on liste les tous les recruteurs de plus de 10 ans d'XP
    Alors on récupères les recruteurs suivants
      | id | email               | competence     | disponible |
      | 1  | recruteur10@soat.fr | Java 10 ans XP | true       |
      | 2  | recruteur11@soat.fr | Java 11 ans XP | true       |

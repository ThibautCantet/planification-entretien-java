# language: fr
Fonctionnalité: Lister les recruteurs expérimentés

  Scénario: Lister les entretiens recruteurs de plus de 10 ans d'XP
    Etant donné les recruteurs existants
      | id                                   | email               | language | xp |
      | 123e4567-e89b-42d3-a456-556642440001 | recruteur10@soat.fr | Java     | 10 |
      | 123e4567-e89b-42d3-a456-556642440002 | recruteur11@soat.fr | Java     | 11 |
      | 123e4567-e89b-42d3-a456-556642440003 | recruteur9@soat.fr  | Java     | 9  |
    Quand on liste les tous les recruteurs de plus de 10 ans d'XP
    Alors on récupères les recruteurs suivants
      | id                                   | email               | competence     |
      | 123e4567-e89b-42d3-a456-556642440001 | recruteur10@soat.fr | Java 10 ans XP |
      | 123e4567-e89b-42d3-a456-556642440002 | recruteur11@soat.fr | Java 11 ans XP |

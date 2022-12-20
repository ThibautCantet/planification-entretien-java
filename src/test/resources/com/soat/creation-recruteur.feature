# language: fr
Fonctionnalité: Création d'un recruteur

  Scénario: Un recruteur est crée quand toutes ses informations sont complètes
    Etant donné un recruteur "Java" ("recruteur@soat.fr") avec "3" ans d’expériences
    Quand on tente d'enregistrer le recruteur
    Alors le recruteur est correctement enregistré avec ses informations "Java", "recruteur@soat.fr" et "3" ans d’expériences

  Scénario: Un recruteur n'est pas crée quand sont email n'est pas soat.fr
    Etant donné un recruteur "Java" ("recruteur@mail.com") avec "3" ans d’expériences
    Quand on tente d'enregistrer le recruteur
    Alors l'enregistrement du recruteur est refusé
    Et le recruteur n'est pas enregistré

  Scénario: Un recruteur n'est pas crée quand sa techno principale est vide
    Etant donné un recruteur "" ("recruteur@soat.fr") avec "3" ans d’expériences
    Quand on tente d'enregistrer le recruteur
    Alors l'enregistrement du recruteur est refusé
    Et le recruteur n'est pas enregistré

  Scénario: Un recruteur n'est pas crée quand son nombre d'années d'expérience est vide
    Etant donné un recruteur "Java" ("recruteur@soat.fr") avec "" ans d’expériences
    Quand on tente d'enregistrer le recruteur
    Alors l'enregistrement du recruteur est refusé
    Et le recruteur n'est pas enregistré

  Scénario: Un recruteur n'est pas crée quand son nombre d'années d'expérience est inférieur à 3
    Etant donné un recruteur "Java" ("recruteur@soat.fr") avec "2" ans d’expériences
    Quand on tente d'enregistrer le recruteur
    Alors l'enregistrement du recruteur est refusé
    Et le recruteur n'est pas enregistré

  Scénario: Un recruteur n'est pas crée quand son email est vide
    Etant donné un recruteur "Java" ("") avec "3" ans d’expériences
    Quand on tente d'enregistrer le recruteur
    Alors l'enregistrement du recruteur est refusé
    Et le recruteur n'est pas enregistré

  Scénario: Un recruteur n'est pas crée quand son email est incorrect
    Etant donné un recruteur "Java" ("recruteur@email") avec "3" ans d’expériences
    Quand on tente d'enregistrer le recruteur
    Alors l'enregistrement du recruteur est refusé
    Et le recruteur n'est pas enregistré

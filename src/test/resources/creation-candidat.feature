# language: fr
Fonctionnalité: Création d'un candidat

  Scénario: Un candidat est crée quand toutes ses informations sont complètes
    Etant donné un candidat "Java" ("candidat@email.com") avec 2 ans d’expériences
    Quand on tente d'enregistrer le candidat
    Alors le candidat est correctement enregistré avec ses informations "Java", "candidat@email.com" et 2 ans d’expériences

  Scénario: Un candidat n'est pas crée quand sa techno principale est vide
    Etant donné un candidat "" ("candidat@email.com") avec 2 ans d’expériences
    Quand on tente d'enregistrer le candidat
    Alors l'enregistrement est refusé
    Et le candidat n'est pas enregistré

  Scénario: Un candidat n'est pas crée quand son nombre d'années d'expérience est vide
    Etant donné un candidat "Java" ("candidat@email.com")
    Quand on tente d'enregistrer le candidat
    Alors l'enregistrement est refusé
    Et le candidat n'est pas enregistré

  Scénario: Un candidat n'est pas crée quand son nombre d'années d'expérience est négatif
    Etant donné un candidat "Java" ("candidat@email.com") avec -1 ans d’expériences
    Quand on tente d'enregistrer le candidat
    Alors l'enregistrement est refusé
    Et le candidat n'est pas enregistré

  Scénario: Un candidat n'est pas crée quand son email est vide
    Etant donné un candidat "Java" ("") avec 2 ans d’expériences
    Quand on tente d'enregistrer le candidat
    Alors l'enregistrement est refusé
    Et le candidat n'est pas enregistré

  Scénario: Un candidat n'est pas crée quand son email est incorrect
    Etant donné un candidat "Java" ("candidat@email") avec 2 ans d’expériences
    Quand on tente d'enregistrer le candidat
    Alors l'enregistrement est refusé
    Et le candidat n'est pas enregistré

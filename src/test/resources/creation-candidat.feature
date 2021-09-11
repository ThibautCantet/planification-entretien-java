# language: fr
Fonctionnalité: Création d'un candidat

  Scénario: Un candidat est crée quand toutes ses informations sont complètes
    Etant donné un candidat "Java" ("candidat@email.com") avec "2" ans d’expériences
    Quand on tente de l'enregistrer
    Alors il est correctement enregistré avec ses informations "Java", "candidat@email.com" et "2" ans d’expériences

  Scénario: Un candidat n'est pas crée quand sa techno principale est vide
    Etant donné un candidat "" ("candidat@email.com") avec "2" ans d’expériences
    Quand on tente de l'enregistrer
    Alors l'enregistrement est refusé pour le motif "Techno invalide : language"
    Et le candidat n'est pas enregistré

  Scénario: Un candidat n'est pas crée quand son nombre d'années d'expérience est vide
    Etant donné un candidat "Java" ("candidat@email.com") avec "" ans d’expériences
    Quand on tente de l'enregistrer
    Alors l'enregistrement est refusé pour le motif "Années d'expérience invalide"
    Et le candidat n'est pas enregistré

  Scénario: Un candidat n'est pas crée quand son nombre d'années d'expérience est négatif
    Etant donné un candidat "Java" ("candidat@email.com") avec "-1" ans d’expériences
    Quand on tente de l'enregistrer
    Alors l'enregistrement est refusé pour le motif "Années d'expérience invalide"
    Et le candidat n'est pas enregistré
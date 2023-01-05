# Sous domaines : modèles spécifique

### Etape 3.1

Séparer les modèles `Candidat` et `Recruteur` dans le sous-domaine `planification_entretien`.

### Question 1 :

Que constatez-vous avec ces nouveaux modèles ?

### Question 2 :

Que constatez-vous avec dépendances ?

### Etape 3.2

Créer un nouveau `value object` `Profil` pour encapsuler les règles de validation concernant la capacité d'un recruteur
à évaluer un candidat.
Créer une méthode dans l'entité `Recruteur` une méthode `boolean estCompatible(Candidat candidat)` déléguant aux profils
les règles de validation. 

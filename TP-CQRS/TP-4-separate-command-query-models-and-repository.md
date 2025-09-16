# Sous domaines

### Etape 5

Dupliquer les modèles se trouvant dans les packages `command` et adapter les pour les besoins de lecture dans les
packages `query`.

Attention, on n'a pas besoin de toute la complexité qu'il existe dans les modèles du domain. On n'a pas besoin de value
object ou de validation dans les constructeurs.

On a seulement besoin d'objets exposant quelques propriété int ou string.

### Etape 6

Séparer les méthodes des `repository` dans des `dao` à placer dans les packages `application`.

Faire les implémentations des `dao` dans les packages `infrastructure`.

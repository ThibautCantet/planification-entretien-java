# Repository et DAO

### Etape 7

Séparer et déplacer les `repository` et les `dao` dans les sous packages `command` et `query` en intégrant des nouveaux
packages `infrastructure` dans chacun de ces précédents packages.

#### Exemple

entretien :

- command
  - domain
    - repository
      - port
  - infrastructure
    - repository
      - adapter
- query
  - application
    - dao
      - port
  - infrastructure
    - dao
      - adapter

### Question

Quels problèmes / questions sont apparus ?

# Projection

### Etape 9

L'idée est d'avoir une projection des recrut.eur.euse.s experimenté.e.s au lieu de faire une requête filtrant par XP et
formattant les informations.

C'est-à-dire avoir stocker exactement ce qu'on veut avoir en retour par la query :

- format de la donnée
- donnée déjà filtrée

#### Etape 9.1

Créer un nouveau modèle de persitance adapté à la query.

Créer une nouvelle une entité hibernate stockant les informations nécessaires au
queryHandler `ListerRecruteursExperimentesQueryHandler`.

```java

@Entity
public class JpaRecruteurDetail {
    @Id
    private Integer id;

    @Column
    private String competence;
    @Column
    private String email;
//TODO
```

Créer le `repository JPA correspondant` :

```java

@Repository
public interface RecruteurDetailCrud extends JpaRepository<JpaRecruteurDetail, Integer> {
}
```

Modifier `HibernateRecruteurDao` pour qu'il dépende du nouveau modèle.

```java

@Repository
public class HibernateRecruteurDao implements RecruteurDao {
    private final RecruteurDetailCrud recruteurDetailCrud;

    @Override
    public List<RecruteurDetail> find10AnsExperience() {
        //TODO
    }

    @Override
    public void addExperimente(RecruteurDetail recruteurDetail) {
        //TODO
    }
```

#### Etape 9.2

Créer un événement `RecruteurCrée`

```java
public record RecruteurCrée(Integer id, String language, Integer experienceInYears, String email) implements Event {
}
```

#### Etape 9.3

Lever l'event `RecruteurCrée` dans `CreerRecruteurCommandHandler`.

```java
public Integer handle(CreerRecruteurCommand creerRecruteurCommand){
        ...
        messageBus.send(new RecruteurCrée(savedRecruteur.getId(),
        savedRecruteur.getLanguage(),
        savedRecruteur.getExperienceInYears(),
        savedRecruteur.getAdresseEmail()
        ));
        ...
```

#### Etape 9.4

Créer un listener `RecruteurCréeListener` dans `recruteur.query.application` :

```java

@Service
public class RecruteurCréeListener implements Listener<Event> {
    private final RecruteurDao recruteurDao;
    private final MessageBus messageBus;

    public RecruteurCréeListener(RecruteurDao recruteurDao, MessageBus messageBus) {
        this.recruteurDao = recruteurDao;
        this.messageBus = messageBus;
        this.messageBus.subscribe(this);
    }

    @Override
    public void onMessage(Event event) {
        //TODO enregistrer dans le DAO directement ce qui sera retourné 
        recruteurDao.addExperimente(new RecruteurDetail(...));
    }
}
```

#### Etape 9.4

Couper la dépendance entre `InMemoryRecruteurDao` et `InMemoryRecruteurRepository`.

Modifier `InMemoryRecruteurDao` pour stocker les objets que l'on veut retourner :

```java
public class InMemoryRecruteurDao implements RecruteurDao {
    private final List<RecruteurDetail> cache = new ArrayList<>();

    @Override
    public List<RecruteurDetail> find10AnsExperience() {
        // TODO
    }

    @Override
    public void addExperimente(RecruteurDetail recruteurDetail) {
        // TODO
    }
}
```

#### Etape 9.5

Pour que le listener soit bien enregistré, il faut modifier l'étape suivante dans la classe `RecruteurATest.cs`
pour que ce soit un `CreerRecruteurCommandHandler` qui sauvegarder un recruteur et non pas directement le repository

```java

@Autowired
private CreerRecruteurCommandHandler creerRecruteurCommandHandler;

@Etantdonné("les recruteurs existants")
public void lesRecruteursExistants(DataTable dataTable){
        List<Recruteur> recruteurs=dataTableTransformEntries(dataTable,this::buildRecruteur);

        for(Recruteur recruteur:recruteurs){
        Integer id=creerRecruteurCommandHandler.handle(new CreerRecruteurCommand(
        recruteur.getLanguage(),
        recruteur.adresseEmail(),
        String.valueOf(recruteur.getExperienceInYears())));
        recruteur=new Recruteur(id,recruteur.getLanguage(),recruteur.adresseEmail(),recruteur.getExperienceInYears());
        savedRecruteurs.add(recruteur);
        }
        }
```

### Question

Quels avantages voyez vous avez les projections ?

### Réponses

On retourne directement ce qu'on souhaite sans avoir à traduire quoi que ce soit.

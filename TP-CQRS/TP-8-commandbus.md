# Command bus

### Etape 10

De nouvelles classes et interfaces ont été ajoutées dans `common.cqrs`

### Etape 10.1 `CommandBusFactory`

Ajouter les différents `commandHandler` dans la class `CommandBusFactory`

````java
 protected List<CommandHandler> getCommandHandlers(){
        return List.of(
        //TODO ajouter les command handler
        );
        }
````

### Etape 10.2 `CommandHandler`

Supprimer les annotations `@Service` des `commandHandler` qui ne sont plus nécessaires.

Etendez les `commandHandler` de `CommandHandler<MyCommand, CommandResponse<Event>>` et modifier la méthode `handle` pour
qu'elle retourn une `CommandResponse<Event>` :

```java
@Override
public CommandResponse<Event> handle(myCommand myCommand){
```

Adapter le corps de la méthode `handle` pour le `return`.

Implémenter la méthode `listenTo()` :

```java
@Override
public Class listenTo(){
        //TODO
        }
```

### Etape 10.3 `Controller`

Faire étendre les `controller` de command de la class `CommandController`.

Supprimer les `commandHandler` des `controller` et dispatcher les `command` sur le `bus` grâce à la
méthode `getCommandBus().dispatch(new MyCommand());` de la classe mère `CommandController`.

Adapter le retour de la méthode `getCommandBus().dispatch()` qui retourne désormais une `CommandResponse`.

### Etape 10.4 `ListerEntretienATest`

Attention, dans la classe `ListerEntretienATest`, il faut modifier le `setup` pour l'insertion des recruteurs :

```java
    @Autowired
private RecruteurRepository recruteurRepository;
@Autowired
private MessageBus messsageBus;
private CreerRecruteurCommandHandler creerRecruteurCommandHandler;

@Before
@Override
public void setUp(){
        initIntegrationTest();
        creerRecruteurCommandHandler=new CreerRecruteurCommandHandler(recruteurRepository,messsageBus);
        }
```

et dans la méthode `lesRecruteursExistants` :

```java
var commandResponse=creerRecruteurCommandHandler.handle(new CreerRecruteurCommand(
        recruteur.getLanguage(),
        recruteur.adresseEmail(),
        String.valueOf(recruteur.getExperienceInYears())));
        Integer id=commandResponse
        .findFirst(RecruteurCrée.class)
        .map(RecruteurCrée.class::cast)
        .map(RecruteurCrée::id)
        .orElse(-1);
```

### Question

Qu'est-ce que cela change de passer par un `command bus` ?

### Réponse

Cela permet de :

- découpler les `controller` des `commandHandler`.
- factoriser toute la gestion des `command` en utilisant un même `commandBus` (logs, events...)

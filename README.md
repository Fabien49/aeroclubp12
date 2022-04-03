#Bibliothequeocp7

---

## Présentation

Bibliothequeocp7 est un site web dédié aux usagers du réseau de bibliothèques du même nom, cette application web JEE a été réalisée dans le cadre du parcours Développeur d'application Java de la plateforme d'enseignement OpenClassrooms.

Les compétences évaluées sont les suivantes:

* Concevoir une application web avec une approche par composants
* Respecter les bonnes pratiques de développement en vigueur
* Créer une API web avec un microservice REST
* Sélectionner les langages de programmation adaptés pour le développement de l’application
* Interagir avec des composants externes

Les fonctions suivantes sont implémentées:

* Rechercher des ouvrages et voir le nombre d’exemplaires disponibles.
* Permettre aux usagers de consulter leurs prêts en cours. Les prêts sont pour une période de 4 semaines.
* Prolonger un prêt en cours. Le prêt d’un ouvrage n’est prolongeable qu’une seule fois. La prolongation ajoute une nouvelle période de prêt (4 semaines) à la période initiale.
* Login via un formulaire

## Guide de démarrage

### Prérequis

* _Java-8_ et plus, disponible [ici](https://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)
* _SpringBoot-2.2.1_ et plus, disponible [ici](https://start.spring.io/)
* _MySQL-8.0.28_ et plus, système de gestion de base de données, disponible [ici](https://dev.mysql.com/downloads/installer/).
* _phpmyadmin-5.1.3_ et plus, outil d'administration de MySQL, disponible [ici](https://www.phpmyadmin.net/downloads/).
* _Maven-3.6.1_ et plus, outil de gestion et d'automatisation de production des projets logiciels, disponible [ici](https://maven.apache.org/download.cgi)
* Le répertoire du projet, disponible [ici](https://github.com/Fabien49/bibliothequeocp7)
* _FakeSmtp-2.0_ et plus, emulateur de serveur smtp pour tester l'envoi de mails, disponible [ici](http://nilhcem.com/FakeSMTP/download.html)

### Paramétrage

Chaque service dispose à l'intérieur de son répertoire, à l'emplacement /src/main/resources d'un fichier application.properties. Un fichier application-prod.properties et application-dev.properties sont en plus au niveau de l'API. 
Ceux-ci permetent de paramétrer certaines propriétés comme l'exemple ci-dessous:

#### Port

```properties
server.port=8001
```

#### Nom de l'application

```properties
spring.application.name=biblio-api
```

#### Données de connexion à la base de données (API seulement) en mode prod (fichier application-prod.properties)

```properties
# ===============================
# = DATA SOURCE
# ===============================
spring.datasource.url= jdbc:mysql://localhost:3306/bibliop7?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
spring.datasource.username= root
spring.datasource.password= admin
spring.datasource.testWhileIdle = true


# ===============================
# = JPA / HIBERNATE
# ===============================
spring.jpa.show-sql = true
spring.jpa.hibernate.ddl-auto = update
spring.jpa.hibernate.naming-strategy = org.hibernate.cfg.ImprovedNamingStrategy
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
```

#### Données de connexion à la base de données H2 (API seulement) en mode dev (fichier application-dev.properties)

```properties
# H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2web
spring.h2.console.settings.web-allow-others=true

# Datasource
spring.datasource.url=jdbc:h2:mem:contactmanager;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
# Hibernate ddl auto (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto=create-drop
```

#### Identifiants Basic Auth (API seulement)

```properties
spring.security.user.name=fabien
spring.security.user.password=123456
```

#### Nom et URL de l'API (Webapp et Batch seulement)

```properties
api.name=biblio-api
api.url=localhost:8001
```

#### Cron expression | Heure d'envoi des mails de relance (Batch seulement)

```properties
batch.time.event=00 00 10 * * ?
```

Seconde=00  
Minute=00  
Heure=10

Dans cet exemple, l'envoi des mails de relance ce fera tous les jours à 10h 00min 00s. Il est modifiable dans le fichier application.properties du Batch.

#### Configuration du compte SMTP

##### Fake SMTP

```properties
mail.protocol=smtp
mail.host=localhost
mail.port=25
mail.smtp.auth=true
mail.smtp.starttls.enable=true
mail.from=bibliotheques-angers@localhost
mail.username=""
mail.password=""
```

## Démarrage

### I. Développement

1. Activer le mode développeur dans le fichier application.properties du répertoire /backendApiBiblio
* Mettre en commentaire la ligne (mettre un # devant la ligne comme ci-dessous) qu'on souhaite désactiver si ce n'est pas déjà le cas.
 ```properties
#spring.profiles.active=prod  
spring.profiles.active=dev
``` 

2. Ouvrez le répertoire du service choisi, ex: /backendApiBiblio

3. Ouvrez un terminal à cet emplacement

4. Exécuter la commande suivante:

```terminal
mvn spring-boot:run
```
Cette commande va compiler le code, générer le package .jar et lancer l'application sous Tomcat.

### III. Production

1. Activer le mode production dans le fichier application.properties du répertoire /backendApiBiblio
* Mettre en commentaire la ligne (mettre un # devant la ligne comme ci-dessous) qu'on souhaite désactiver si ce n'est pas déjà le cas.
 ```properties
spring.profiles.active=prod  
#spring.profiles.active=dev
``` 

2. Ouvrez le répertoire du service choisi, ex: /backendApiBiblio/target

3. Ouvrez un terminal à cet emplacement

4. Exécuter la commande suivante:

```terminal
java -jar application.jar
```

Remplacez application.jar par le nom de l'application à lancer.

Cette commande va compiler le code, générer le package .jar et lancer l'application sous Tomcat.


### Import des données de démonstration en mode développeur

1. Activer le mode développeur dans le fichier application.properties du répertoire /backendApiBiblio
* Mettre en commentaire la ligne (mettre un # devant la ligne comme ci-dessous) qu'on souhaite désactiver si ce n'est pas déjà le cas.  
 ```properties
#spring.profiles.active=prod  
spring.profiles.active=dev
``` 
2. Lancer le service api pour générer les tables dans la base de données, il lira automatiquement le fichier _data.sql_ dans /ressources. Ce dernier insèrera les données dans les différentes tables créées au préalable lors de lancement de l'api.

### Import des données de démonstration

1. Lancer une première fois le service api pour générer les tables dans la base de données.

2. À l'aide de phpMyAdmin, éxécuter le script sql _data_demo.sql_.

Ce fichier se trouve dans le répertoire /database

### Utilisateur enregistré

Identifiant : fabien@gmail.com  
Mot de passe : 123456

## Technologies utilisées

* JEE
* Spring
    * Spring Boot
    * Spring Data JPA
    * Spring Security
    * Spring MVC
* Rest
* OpenFeign
* Swagger
* Thymleaf
* Bootstrap
* Maven

## Aperçu du site

URL : http://localhost:8002

## Auteur

* **Fabien Chapeau** - [Fabien49](https://github.com/Fabien49)

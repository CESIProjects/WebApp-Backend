# Getting Started

Pour lancer le project vous devrez installer Java JDK :
[Cliquez ici :](https://www.oracle.com/java/technologies/downloads/#jdk21-windows)

Ensuite vous devrez installer Maven et le définir dans vos variable d'environement :
[Suivre ce tuto en cliquant ici :](https://phoenixnap.com/kb/install-maven-windows)

Ensuite cloner le git du backend avec : 

```git clone https://github.com/CESIProjects/WebApp-Backend.git```

Ensuite :

```cd WebApp-Backend```

Une fois dans l'application, installer toute les dépendances établit : 

```mvn clean install```

Si la commande est un succès vous pourrez allumé le serveur avec :

```mvn spring-boot:run```

Et rendez-vous sur http://localhost:8080


### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.5/maven-plugin/reference/html/#build-image)


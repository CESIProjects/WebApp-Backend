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

### SonarQube
Step 1 - Download SonarQube on the official Website and take note of the version (9.9.3 for me) (url for download : https://www.sonarsource.com/products/sonarqube/downloads/) If, when you lauch http://localhost:9000/, you find the page, you're strong enough to continue.

Step 2 - Unzip the folder, then go to bin -> windows-x86-64 -> run StartSonar

Step 3 - Go to your Windows Environements Varibales and Create a system variable named "SONAR_TOKEN" with the value "squ_46c9c1e8595241651fce9bb5face8f0d8707793c"

Step 4 - Access http://localhost:9000/, connect with admin / admin and change your password to RessourcesRelationnelles2024

Step 5 - Launch "mvn verify sonar:sonar" (verify is essential to make JaCoco work)

Step 6 - Check coverage on SonarQube

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.5/maven-plugin/reference/html/#build-image)

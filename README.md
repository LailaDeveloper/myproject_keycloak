# Intégration de Keycloak avec une application Spring Boot et Thymeleaf

## Prérequis
Avant de commencer, assurez-vous d'avoir les éléments suivants installés :
- **Java 17**
- **Maven**
- **Keycloak** installé et configuré via Docker
- **Spring Boot 3.4.3** avec Thymeleaf

## Ajout des dépendances Keycloak
Ajoutez les dépendances suivantes dans votre fichier `pom.xml` :

```xml
<dependencies>
    <!-- Intégration de Keycloak avec Spring Boot -->
    <dependency>
        <groupId>org.keycloak</groupId>
        <artifactId>keycloak-spring-boot-starter</artifactId>
        <version>22.0.5</version>
    </dependency>

    <!-- Adaptation de Keycloak à Spring Security -->
    <dependency>
        <groupId>org.keycloak</groupId>
        <artifactId>keycloak-spring-security-adapter</artifactId>
        <version>22.0.5</version>
    </dependency>

    <!-- Client OAuth2 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-client</artifactId>
    </dependency>

    <!-- Validation des jetons JWT -->
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-oauth2-jose</artifactId>
    </dependency>

    <!-- Protection des endpoints -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
    </dependency>
</dependencies>
```

## Configuration de Keycloak

### Points Clés de la Configuration
Dans `application.properties`, configurez Keycloak avec les paramètres suivants :

```properties
keycloak.realm=intelcom_platform
keycloak.auth-server-url=http://localhost:8080
keycloak.resource=client1
keycloak.logout-url=http://localhost:8080/realms/intelcom_platform/protocol/openid-connect/logout

spring.security.oauth2.client.registration.keycloak.client-id=client1
spring.security.oauth2.client.registration.keycloak.client-secret=<SECRET>
spring.security.oauth2.client.registration.keycloak.scope=openid
spring.security.oauth2.client.provider.keycloak.issuer-uri=http://localhost:8080/realms/intelcom_platform
```

## Architecture du Projet

```
│   ApplicationKeycloakApplication.java
│
├───controller
│       HomeController.java
│       RedirectController.java
│       ServiceController.java
│
├───security
│       CustomJwtAuthenticationConverter.java
│       KeycloakSecurityConfig.java
│
└───resources
│   application.properties
│
├───static
│   └───css
│           styles.css
│
└───templates
    ├── dashboard.html
    ├── home.html
    └── products.html
```

## Démarrage du Projet

### 1. Démarrer Keycloak
Lancez Keycloak via Docker :

```sh
docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:22.0.5 start-dev
```

### 2. Configurer Keycloak
- **Créez un realm** : `intelcom_platform`
- **Ajoutez un client** : `client1`
    - URL de redirection : `http://localhost:8081/*`

### 3. Démarrer l'Application Spring Boot
Exécutez la commande suivante :

```sh
mvn spring-boot:run
```

### 4. Accéder à l'Application
Ouvrez votre navigateur et accédez à :

```
http://localhost:8081
```

Connectez-vous avec un utilisateur Keycloak pour accéder à l'application.

## Points Clés
- **Authentification centralisée** : Keycloak gère les utilisateurs et les rôles.
- **Gestion flexible des rôles et permissions** : Administrable depuis Keycloak.
- **Sécurisation robuste** : Protection des endpoints et validation des JWT avec Spring Security.

## Conclusion
Cette intégration permet une gestion centralisée des utilisateurs et des accès via Keycloak, simplifiant la sécurisation des applications Spring Boot tout en garantissant une expérience utilisateur fluide.


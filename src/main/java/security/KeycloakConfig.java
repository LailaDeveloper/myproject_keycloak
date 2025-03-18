package security;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    /** pour configurer keycloak via le fichier application.properties
     * sans avoir a utilise le fichier keycloack.json
      */
    @Bean
    KeycloakConfigResolver configResolver(){
        return new KeycloakSpringBootConfigResolver();
    }
}

package security;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@KeycloakConfiguration
@EnableWebSecurity
public class KeycloakSpringSecurityConfig {

    /**
     * Configure le fournisseur d'authentification Keycloak.
     * KeycloakAuthenticationProvider est responsable de l'intégration avec Spring Security.
     */
    @Bean
    public KeycloakAuthenticationProvider keycloakAuthenticationProvider() {
        return new KeycloakAuthenticationProvider();
    }

    /**
     * Configure l'AuthenticationManagerBuilder pour utiliser le fournisseur d'authentification Keycloak.
     * C'est ce qui relie Spring Security avec Keycloak pour gérer les utilisateurs et leurs rôles.
     */
    @Bean
    public AuthenticationManagerBuilder authenticationManagerBuilder(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(keycloakAuthenticationProvider());
        return auth;
    }

    /**
     * Configure les règles de sécurité HTTP.
     * L'accès aux services est protégé en fonction des rôles définis dans Keycloak.
     */
    @Bean
    public HttpSecurity httpSecurity(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/service1").hasRole("SERVICE_1_ACCESS")
                        .requestMatchers("/service2").hasRole("SERVICE_2_ACCESS")
                          // Nécessite une authentification
                )
                .csrf(csrf -> csrf.disable())  // Désactivation de CSRF si nécessaire (pour simplification)
                .sessionManagement(session ->
                        session.sessionAuthenticationStrategy(sessionAuthenticationStrategy())  // Gestion de session
                )
                .formLogin().loginPage("/auth")  // Utilisation de la page de login de Keycloak
                .permitAll();// Utilisation de OAuth2 pour la gestion de l'authentification via Keycloak

        return http;
    }


    /**
     * Gère la stratégie de session après l'authentification.
     * Utilisation de RegisterSessionAuthenticationStrategy avec SessionRegistryImpl pour suivre les sessions.
     */
    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }
}

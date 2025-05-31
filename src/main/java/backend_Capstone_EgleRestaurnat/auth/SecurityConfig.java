package backend_Capstone_EgleRestaurnat.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // Disabilita CSRF (non necessario per API REST)
                .authorizeHttpRequests(authorize -> authorize
                        // Accesso pubblico per autenticazione
                        // Permette a tutti di accedere alle API di login e registrazione
                        .requestMatchers("/api/auth/**").permitAll()
                        
                        // Solo gli ADMIN possono gestire le categorie
                        // Questo include creazione, modifica e eliminazione di categorie
                        // Tutti gli utenti possono vedere le categorie (GET)
                        .requestMatchers("/api/categories/**").hasRole("ADMIN")
                        
                        // Solo gli ADMIN possono gestire i piatti
                        // Questo include creazione, modifica e eliminazione di piatti
                        // Tutti gli utenti possono vedere i piatti (GET)
                        .requestMatchers("/api/dishes/**").hasRole("ADMIN")
                        
                        // Solo gli ADMIN possono gestire gli utenti
                        // Questo include creazione, modifica, eliminazione e blocco/sblocco di utenti
                        .requestMatchers("/api/users/**").hasRole("ADMIN")
                        
                        // Eccezione per la visualizzazione dei piatti
                        // Tutti gli utenti (USER e ADMIN) possono vedere i piatti
                        // Questo sovrascrive la regola precedente per /api/dishes/**
                        .requestMatchers("/api/dishes").permitAll()
                        
                        // Tutte le altre richieste richiedono autenticazione
                        // Questo include tutte le API non specificamente menzionate sopra
                        .anyRequest().authenticated()
                )
                
                // Gestione delle eccezioni di autenticazione
                // Quando un utente non autenticato tenta di accedere a una risorsa protetta
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                
                // Gestione delle sessioni
                // Sessione stateless per JWT
                // Ogni richiesta deve includere il token JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // Aggiungi il filtro JWT
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package backend_Capstone_EgleRestaurnat.auth;

import backend_Capstone_EgleRestaurnat.email.EmailSenderService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class AppUserService {
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private EmailSenderService emailSenderService;

    public AppUser registerUser(String email, String password, String nome, String cognome, Set<Role> roles) {
        // Validazione base dell'email
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("L'email non può essere vuota");
        }
        
        // Verifica che l'email contenga @ e un dominio
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Formato email non valido");
        }

        if (appUserRepository.existsByEmail(email)) { 
            throw new EntityExistsException("Email già in uso");
        }

        AppUser appUser = new AppUser();
        appUser.setEmail(email);
        appUser.setPassword(passwordEncoder.encode(password));
        appUser.setNome(nome);
        appUser.setCognome(cognome);
        appUser.setRoles(roles);
        appUser.setEnabled(true);
        appUser.setAccountNonExpired(true);
        appUser.setAccountNonLocked(true);
        appUser.setCredentialsNonExpired(true);
        appUser.setIsLoggedIn(false);
        appUser.setIsBlocked(false);

        // Salva l'utente
        AppUser savedUser = appUserRepository.save(appUser);

        // Invia la mail di conferma
        try {
            emailSenderService.sendRegistrationEmail(savedUser.getEmail(), savedUser.getNome(), savedUser.getCognome());
            log.info("Mail di conferma inviata con successo a: " + savedUser.getEmail());
        } catch (Exception e) {
            log.error("Errore nell'invio della mail di conferma a {}: {}", savedUser.getEmail(), e.getMessage());
            // Non blocchiamo la registrazione se fallisce l'invio della mail
        }

        return savedUser;
    }

    public Optional<AppUser> findByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    public String authenticateUser(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            AppUser appUser = (AppUser) userDetails;
            appUser.updateLoginStatus(true);
            appUserRepository.save(appUser);
            return jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Credenziali non valide", e);
        }
    }

    public AppUser loadUserByEmail(String email) {
        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato con email: " + email));

        return appUser;
    }

    public void blockUser(Long userId) {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato con ID: " + userId));

        user.toggleBlockStatus();
        appUserRepository.save(user);
    }

    public void unblockUser(Long userId) {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato con ID: " + userId));

        user.toggleBlockStatus();
        appUserRepository.save(user);
    }

    public void deleteUser(Long userId) {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato con ID: " + userId));

        appUserRepository.delete(user);
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    public List<AppUser> getActiveUsers() {
        return appUserRepository.findByIsLoggedInTrue();
    }

    public List<AppUser> getBlockedUsers() {
        return appUserRepository.findByIsBlockedTrue();
    }
}

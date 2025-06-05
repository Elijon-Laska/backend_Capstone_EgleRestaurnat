package backend_Capstone_EgleRestaurnat.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class AuthRunner implements ApplicationRunner {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Creazione dell'utente admin se non esiste
        Optional<AppUser> adminUser = appUserService.findByEmail("elijonlaska95@gmail.com");
        if (adminUser.isEmpty()) {
            appUserService.registerUser("elijonlaska95@gmail.com", "Adm1n!pwd", "Admin", "Admin", Set.of(Role.ROLE_ADMIN));
        }

        // Creazione dell'utente user se non esiste
        Optional<AppUser> normalUser = appUserService.findByEmail("user@example.com");
        if (normalUser.isEmpty()) {
            appUserService.registerUser("user@example.com", "userpwd", "Utente", "Test", Set.of(Role.ROLE_USER));
        }

        // Creazione dell'utente seller se non esiste
        Optional<AppUser> normalSeller = appUserService.findByEmail("seller@example.com");
        if (normalSeller.isEmpty()) {
            appUserService.registerUser("seller@example.com", "sellerpwd", "Seller", "Seller", Set.of(Role.ROLE_SELLER));
        }


    }
}

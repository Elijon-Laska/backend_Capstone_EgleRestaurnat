package backend_Capstone_EgleRestaurnat.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AppUserService appUserService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/current-user")
    public AppUser getCurrentUser(@AuthenticationPrincipal AppUser appUser) {
        return appUser;
    }
    @PostMapping("/register")
    @PreAuthorize("permitAll")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        if (!registerRequest.isTermsAccepted()) {
            throw new RuntimeException("Devi accettare i termini e le condizioni");
        }

        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new RuntimeException("Le password non coincidono");
        }

        appUserService.registerUser(
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getNome(),
                registerRequest.getCognome(),
                Set.of(Role.ROLE_USER)
        );

        return ResponseEntity.ok("Registrazione avvenuta con successo");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String token = appUserService.authenticateUser(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );
        return ResponseEntity.ok(new AuthResponse(token));
    }
    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<AppUser>> getAllUsers() {
        return ResponseEntity.ok(appUserService.getAllUsers());
    }

    @GetMapping("/users/active")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<AppUser>> getActiveUsers() {
        return ResponseEntity.ok(appUserService.getActiveUsers());
    }

    @GetMapping("/users/blocked")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<AppUser>> getBlockedUsers() {
        return ResponseEntity.ok(appUserService.getBlockedUsers());
    }

    @PostMapping("/users/{userId}/block")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> blockUser(@PathVariable Long userId) {
        appUserService.blockUser(userId);
        return ResponseEntity.ok("Utente bloccato con successo");
    }

    @PostMapping("/users/{userId}/unblock")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> unblockUser(@PathVariable Long userId) {
        appUserService.unblockUser(userId);
        return ResponseEntity.ok("Utente sbloccato con successo");
    }

    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        appUserService.deleteUser(userId);
        return ResponseEntity.ok("Utente eliminato con successo");
    }
}

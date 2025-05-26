package backend_Capstone_EgleRestaurnat.auth;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
    boolean existsByEmail(String email);


    @Query("SELECT u FROM AppUser u WHERE u.isLoggedIn = true")
    List<AppUser> findByIsLoggedInTrue();

    @Query("SELECT u FROM AppUser u WHERE u.isBlocked = true")
    List<AppUser> findByIsBlockedTrue();

    @Query("SELECT u FROM AppUser u WHERE u.isBlocked = false")
    List<AppUser> findByIsBlockedFalse();

    @Query("SELECT u FROM AppUser u WHERE u.email = ?1 AND u.isBlocked = false")
    Optional<AppUser> findByEmailAndIsBlockedFalse(String email);
}

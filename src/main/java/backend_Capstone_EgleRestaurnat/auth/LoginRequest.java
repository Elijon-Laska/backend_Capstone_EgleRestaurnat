package backend_Capstone_EgleRestaurnat.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String email; // Usa email invece di username
    private String password;
}

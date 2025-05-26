package backend_Capstone_EgleRestaurnat.auth;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String nome;
    private String cognome;
    private String email;
    private String confirmPassword;
    private boolean termsAccepted;
}

package backend_Capstone_EgleRestaurnat.prenotazioni;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrenotazioneRequest {
    @NotBlank(message = "Nome è richiesto")

    @Size(min = 2, message = "Nome deve essere almeno 2 caratteri")
    private String nome;

    @NotBlank(message = "Telefono è richiesto")

    @Size(min = 6, max = 15, message = "Telefono deve avere almeno 6 cifre e al massimo 15 cifre")
    private String telefono;

    @NotBlank(message = "Email è richiesta")
    @Email(message = "Email non valida")
    private String email;

    @NotNull(message = "Numero di persone è richiesto")
    @Min(value = 1, message = "Numero di persone deve essere almeno 1")
    @Max(value = 20, message = "Numero di persone non può superare 20")
    private Integer persone;

    @NotBlank(message = "Data è richiesta")
    private String data;

    @NotBlank(message = "Ora è richiesta")

    private String ora;

    @Size(max = 500, message = "Richieste speciali non possono superare i 500 caratteri")
    private String richieste;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPersone(Integer persone) {
        this.persone = persone;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public void setRichieste(String richieste) {
        this.richieste = richieste;
    }
}

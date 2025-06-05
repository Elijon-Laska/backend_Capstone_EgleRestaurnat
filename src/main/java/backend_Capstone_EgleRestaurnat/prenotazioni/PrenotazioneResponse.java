package backend_Capstone_EgleRestaurnat.prenotazioni;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrenotazioneResponse {
    private Long id;
    private String nome;
    private String telefono;
    private String email;
    private Integer persone;
    private String data;
    private String ora;
    private String richieste;
    private boolean confermata;
    private boolean annullata;
    private String codicePrenotazione;
    private Date dataCreazione;

    public PrenotazioneResponse(Prenotazione prenotazione) {
        this.id = prenotazione.getId();
        this.nome = prenotazione.getNome();
        this.telefono = prenotazione.getTelefono();
        this.email = prenotazione.getEmail();
        this.persone = prenotazione.getPersone();
        this.data = prenotazione.getData();
        this.ora = prenotazione.getOra();
        this.richieste = prenotazione.getRichieste();
        this.confermata = prenotazione.isConfermata();
        this.annullata = prenotazione.isAnnullata();
        this.codicePrenotazione = prenotazione.getCodicePrenotazione();
        this.dataCreazione = prenotazione.getDataCreazione();
    }
}

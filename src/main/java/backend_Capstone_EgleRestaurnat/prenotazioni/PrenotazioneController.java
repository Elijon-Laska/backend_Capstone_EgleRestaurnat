package backend_Capstone_EgleRestaurnat.prenotazioni;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;
    // Solo admin può vedere tutte le prenotazioni
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<PrenotazioneResponse> getAllPrenotazioni() {
        return prenotazioneService.getAllPrenotazioni();
    }

    // Crea una nuova prenotazione (accessibile a tutti)
    @PostMapping
    public ResponseEntity<PrenotazioneResponse> createPrenotazione(@Valid @RequestBody PrenotazioneRequest request) {
        PrenotazioneResponse response = prenotazioneService.createPrenotazione(request);
        return ResponseEntity.ok(response);
    }

    // Ottiene una prenotazione per codice (accessibile a tutti)
    @GetMapping("/{codice}")
    public ResponseEntity<PrenotazioneResponse> getPrenotazione(@PathVariable String codice) {
        PrenotazioneResponse response = prenotazioneService.getPrenotazione(codice);
        return ResponseEntity.ok(response);
    }

    // Conferma una prenotazione (accessibile solo agli admin)
    @PutMapping("/{codice}/conferma")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> confermaPrenotazione(@PathVariable String codice) {
        prenotazioneService.confermaPrenotazione(codice);
        return ResponseEntity.ok("Prenotazione confermata con successo");
    }

    // Annulla una prenotazione (accessibile solo agli admin)
    @PutMapping("/{codice}/annulla")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> annullaPrenotazione(@PathVariable String codice) {
        prenotazioneService.annullaPrenotazione(codice);
        return ResponseEntity.ok("Prenotazione annullata con successo");
    }
}

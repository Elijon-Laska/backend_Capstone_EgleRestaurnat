package backend_Capstone_EgleRestaurnat.prenotazioni;

import backend_Capstone_EgleRestaurnat.email.EmailSenderService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Transactional
    public PrenotazioneResponse createPrenotazione(PrenotazioneRequest request) {
        // Genera un codice univoco per la prenotazione
        String codicePrenotazione = "EGL" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + UUID.randomUUID().toString().substring(0, 4);

        // Crea la prenotazione
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setNome(request.getNome());
        prenotazione.setTelefono(request.getTelefono());
        prenotazione.setEmail(request.getEmail());
        prenotazione.setPersone(request.getPersone());
        prenotazione.setData(request.getData());
        prenotazione.setOra(request.getOra());
        prenotazione.setRichieste(request.getRichieste());
        prenotazione.setConfermata(false);
        prenotazione.setAnnullata(false);
        prenotazione.setCodicePrenotazione(codicePrenotazione);
        prenotazione.setToken(UUID.randomUUID().toString());

        // Salva la prenotazione
        prenotazione = prenotazioneRepository.save(prenotazione);

        try {
            // Invia email di conferma al cliente
            emailSenderService.sendReservationConfirmationEmail(
                request.getEmail(),
                request.getNome(),
                "",
                codicePrenotazione,
                request.getData(),
                request.getPersone()
            );
        } catch (MessagingException e) {
            log.error("Errore nell'invio dell'email di conferma: {}", e.getMessage());
            throw new RuntimeException("Errore nell'invio dell'email di conferma", e);
        }

        return new PrenotazioneResponse(prenotazione);
    }

    public PrenotazioneResponse getPrenotazione(String codice) {
        Prenotazione prenotazione = prenotazioneRepository.findByCodicePrenotazione(codice);
        if (prenotazione == null) {
            throw new RuntimeException("Prenotazione non trovata");
        }
        return new PrenotazioneResponse(prenotazione);
    }
    public List<PrenotazioneResponse> getAllPrenotazioni() {
        return prenotazioneRepository.findAll()
                .stream()
                .map(PrenotazioneResponse::new)
                .toList();
    }

    @Transactional
    public void confermaPrenotazione(String codice) {
        Prenotazione prenotazione = prenotazioneRepository.findByCodicePrenotazione(codice);
        if (prenotazione == null) {
            throw new RuntimeException("Prenotazione non trovata");
        }
        if (prenotazione.isAnnullata()) {
            throw new IllegalStateException("Prenotazione già annullata");
        }
        if (prenotazione.isConfermata()) {
            throw new IllegalStateException("Prenotazione già confermata");
        }
        prenotazione.setConfermata(true);
        prenotazioneRepository.save(prenotazione);

        try {
            // Invia email di conferma al cliente
            emailSenderService.sendReservationConfirmationEmail(
                prenotazione.getEmail(),
                prenotazione.getNome(),
                "",
                codice,
                prenotazione.getData(),
                prenotazione.getPersone()
            );
        } catch (MessagingException e) {
            log.error("Errore nell'invio dell'email di conferma: {}", e.getMessage());
            throw new RuntimeException("Errore nell'invio dell'email di conferma", e);
        }
    }

    @Transactional
    public void annullaPrenotazione(String codice) {
        Prenotazione prenotazione = prenotazioneRepository.findByCodicePrenotazione(codice);
        if (prenotazione == null) {
            throw new RuntimeException("Prenotazione non trovata");
        }
        if (prenotazione.isAnnullata()) {
            throw new RuntimeException("Prenotazione già annullata");
        }
        prenotazione.setAnnullata(true);
        prenotazioneRepository.save(prenotazione);

        try {
            // Invia email di annullamento al cliente
            emailSenderService.sendSimpleEmail(
                prenotazione.getEmail(),
                "Prenotazione Annullata - Egle Restaurant",
                "La tua prenotazione presso Egle Restaurant è stata annullata.\n\n" +
                "Codice prenotazione: " + codice + "\n\n" +
                "Se hai bisogno di altre informazioni, contattaci al +39 123 456 789\n\n" +
                "Egle Restaurant Team"
            );
        } catch (MessagingException e) {
            log.error("Errore nell'invio dell'email di annullamento: {}", e.getMessage());
            throw new RuntimeException("Errore nell'invio dell'email di annullamento", e);
        }
    }
}

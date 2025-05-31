package backend_Capstone_EgleRestaurnat.prenotazioni;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    Prenotazione findByToken(String token);
    Prenotazione findByCodicePrenotazione(String codice);
}

package backend_Capstone_EgleRestaurnat.prenotazioni;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "prenotazioni")

public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    @Column(updatable = false)
    private String token;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCreazione = new Date();
}
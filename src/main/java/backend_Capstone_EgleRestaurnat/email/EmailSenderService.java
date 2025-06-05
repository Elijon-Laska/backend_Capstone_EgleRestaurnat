package backend_Capstone_EgleRestaurnat.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.name}")
    private String appName;

    @Value("${app.email}")
    private String appEmail;

    @Value("${app.phone}")
    private String appPhone;

    @Value("${app.address}")
    private String appAddress;

    // Email di conferma registrazione
    public void sendRegistrationEmail(String to, String nome, String cognome) throws MessagingException {
        String subject = "Benvenuto su " + appName + "!";
        String body = """
            Caro/a %s %s,

            Benvenuto/a su %s!
            
            La tua registrazione è stata completata con successo. 
            Ora puoi accedere al tuo account utilizzando le credenziali fornite.
            
            Per qualsiasi domanda o assistenza, contattaci:
            Email: %s
            Tel: %s
            Indirizzo: %s
            
            Cordiali saluti,
            Team %s
            """.formatted(nome, cognome, appName, appEmail, appPhone, appAddress, appName);

        sendEmail(to, subject, body);
    }


    // Email di conferma prenotazione
    public void sendReservationConfirmationEmail(String to, String nome, String cognome, String reservationId, String date, int guests) throws MessagingException {
        String subject = "Conferma prenotazione su " + appName;
        String body = """
            Caro/a %s %s,

            La tua prenotazione su %s è stata confermata!
            
            Dettagli prenotazione:
            ID prenotazione: %s
            Data: %s
            Numero di ospiti: %d
            
            Per qualsiasi domanda o assistenza, contattaci:
            Email: %s
            Tel: %s
            Indirizzo: %s
            
            Cordiali saluti,
            Team %s
            """.formatted(nome, cognome, appName, reservationId, date, guests, appEmail, appPhone, appAddress, appName);

        sendEmail(to, subject, body);
    }

    // Email di recupero password
    public void sendPasswordResetEmail(String to, String nome, String cognome, String resetToken) throws MessagingException {
        String subject = "Recupero password su " + appName;
        String body = """
            Caro/a %s %s,

            Hai richiesto il recupero password su %s.
            
            Clicca sul link seguente per resettare la tua password:
            http://localhost:3000/reset-password?token=%s
            
            Se non hai richiesto il recupero password, ignora questa email.
            
            Per qualsiasi domanda o assistenza, contattaci:
            Email: %s
            Tel: %s
            Indirizzo: %s
            
            Cordiali saluti,
            Team %s
            """.formatted(nome, cognome, appName, resetToken, appEmail, appPhone, appAddress, appName);

        sendEmail(to, subject, body);
    }

    // Email di conferma recensione
    public void sendReviewConfirmationEmail(String to, String nome, String cognome, String reviewId) throws MessagingException {
        String subject = "Conferma recensione su " + appName;
        String body = """
            Caro/a %s %s,

            La tua recensione su %s è stata pubblicata!
            
            ID recensione: %s
            
            Grazie per aver condiviso la tua esperienza con noi!
            
            Per qualsiasi domanda o assistenza, contattaci:
            Email: %s
            Tel: %s
            Indirizzo: %s
            
            Cordiali saluti,
            Team %s
            """.formatted(nome, cognome, appName, reviewId, appEmail, appPhone, appAddress, appName);

        sendEmail(to, subject, body);
    }

    // Email di notifica promozioni
    public void sendPromotionEmail(String to, String nome, String cognome, String promotionTitle, String promotionDescription) throws MessagingException {
        String subject = "Nuova promozione su " + appName;
        String body = """
            Caro/a %s %s,

            Hai una nuova promozione su %s!
            
            %s
            
            %s
            
            Visita il nostro sito per approfittarne!
            
            Per qualsiasi domanda o assistenza, contattaci:
            Email: %s
            Tel: %s
            Indirizzo: %s
            
            Cordiali saluti,
            Team %s
            """.formatted(nome, cognome, appName, promotionTitle, promotionDescription, appEmail, appPhone, appAddress, appName);

        sendEmail(to, subject, body);
    }

    // Metodo base per inviare email
    private void sendEmail(String to, String subject, String body) throws MessagingException {
        try {
            log.info("Inizio invio email a: {}", to);
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            // Splitta la stringa "to" usando virgola o punto e virgola come delimitatori
            String[] recipients = to.split("\\s*[,;]\\s*");
            helper.setTo(recipients);

            helper.setSubject(subject);
            helper.setText(body, true);
            
            mailSender.send(message);
            log.info("Email inviata con successo a: {}", to);
        } catch (MessagingException e) {
            log.error("Errore nell'invio dell'email a {}: {}", to, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Errore generale nell'invio dell'email a {}: {}", to, e.getMessage());
            throw new MessagingException("Errore nell'invio dell'email", e);
        }
    }

    // Metodo pubblico per inviare email semplici
    public void sendSimpleEmail(String to, String subject, String body) throws MessagingException {
        sendEmail(to, subject, body);
    }

    // Metodo per inviare email con allegati (es. menu, ricevute)
    public void sendEmailWithAttachment(String to, String nome, String cognome, String subject, String body, byte[] attachmentBytes, String attachmentName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String[] recipients = to.split("\\s*[,;]\\s*");
        helper.setTo(recipients);

        helper.setSubject(subject);
        helper.setText(body, true);
        helper.addAttachment(attachmentName, new ByteArrayResource(attachmentBytes));
        mailSender.send(message);
        System.out.println("Email inviata con successo a " + to + " con allegato: " + attachmentName);
    }
}
package pl.electronic_emergency_departament.webapi.email;

public interface EmailSender {
    void send(String to, String email);
}

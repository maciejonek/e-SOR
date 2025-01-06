package pl.electronic_emergency_departament.webapi.registration;

import lombok.*;

@Data
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
}

package pl.electronic_emergency_departament.webapi.registration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    @JsonCreator
    public RegistrationRequest(@JsonProperty("firstName")String firstName,@JsonProperty("lastName") String lastName, @JsonProperty("email")String email, @JsonProperty("password")String password, @JsonProperty("confirmedPassword")String confirmedPassword, @JsonProperty("pesel")String pesel, @JsonProperty("phoneNumber")String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.confirmedPassword = confirmedPassword;
        this.pesel = pesel;
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty("firstName")
    private final String firstName;

    @JsonProperty("lastName")
    private final String lastName;

    @JsonProperty("email")
    private final String email;

    @JsonProperty("password")
    private final String password;

    @JsonProperty("confirmedPassword")
    private final String confirmedPassword;

    @JsonProperty("pesel")
    private final String pesel;

    @JsonProperty("phoneNumber")
    private final String phoneNumber;
}

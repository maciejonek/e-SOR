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
    public RegistrationRequest(@JsonProperty("first_name")String firstName,@JsonProperty("last_name") String lastName, @JsonProperty("email")String email, @JsonProperty("password")String password, @JsonProperty("confirm_password")String confirm_password, @JsonProperty("pesel_number")String pesel_number, @JsonProperty("phone_number")String phone_number) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.confirm_password = confirm_password;
        this.pesel_number = pesel_number;
        this.phone_number = phone_number;
    }

    @JsonProperty("first_name")
    private final String firstName;

    @JsonProperty("last_name")
    private final String lastName;

    @JsonProperty("email")
    private final String email;

    @JsonProperty("password")
    private final String password;

    @JsonProperty("confirm_password")
    private final String confirm_password;

    @JsonProperty("pesel_number")
    private final String pesel_number;

    @JsonProperty("phone_number")
    private final String phone_number;
}

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
    public RegistrationRequest(@JsonProperty("firstName")String firstName,@JsonProperty("lastName") String lastName, @JsonProperty("email")String email, @JsonProperty("password")String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @JsonProperty("firstName")
    private final String firstName;

    @JsonProperty("lastName")
    private final String lastName;

    @JsonProperty("email")
    private final String email;

    @JsonProperty("password")
    private final String password;
}

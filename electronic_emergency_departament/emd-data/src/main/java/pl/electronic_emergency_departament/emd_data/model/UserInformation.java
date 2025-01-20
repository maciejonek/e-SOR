package pl.electronic_emergency_departament.emd_data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserInformation {
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String email;
    private String peselNumber;
}
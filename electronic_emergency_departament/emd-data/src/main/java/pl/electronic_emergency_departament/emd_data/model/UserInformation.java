package pl.electronic_emergency_departament.emd_data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
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
    private String sex;
    private int age;

    public UserInformation(String name, String surname, String peselNumber, int age) {
        this.name = name;
        this.surname = surname;
        this.peselNumber = peselNumber;
        this.age = age;
    }

}
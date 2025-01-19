package pl.electronic_emergency_departament.webapi.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import pl.electronic_emergency_departament.emd_data.model.*;
import pl.electronic_emergency_departament.emd_data.repositories.UserRepository;
import pl.electronic_emergency_departament.webapi.registration.token.ConfirmationToken;
import pl.electronic_emergency_departament.webapi.registration.token.ConfirmationTokenRepository;
import pl.electronic_emergency_departament.webapi.registration.token.ConfirmationTokenService;
import pl.electronic_emergency_departament.webapi.services.security.model.UserDetailsDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    private JdbcTemplate jdbcTemplate;

     private String PREDICT_URL = "http://3.120.206.66:80/predict";

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));

        return new UserDetailsDto(
                user.getEmail(),
                user.getPassword(),
                true,
                false,
                UserRole.USER,
                user.getUser_id()
        );
    }


    public String signUpUser(Users user) {
        boolean userExist = userRepository
                .findByEmail(user.getEmail())
                .isPresent();

        if (userExist) {
            throw new IllegalStateException("E-mail already taken");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password can not be null or empty");
        }

        String rawPassword = user.getPassword();
        if (rawPassword == null) {
            throw new IllegalArgumentException("Raw password is null");
        }

        String encodePassword = bCryptPasswordEncoder
                .encode(user.getPassword());

        user.setPassword(encodePassword);

        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);


        return token;

    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }

    public UserInformation myProfile(Long userId) {

        try {
            return jdbcTemplate.queryForObject(
                    "SELECT name, surname, date_of_birth, phone_number, email, pesel_number, sex FROM users WHERE user_id = ?",
                    new Object[]{userId},
                    (rs, rowNum) -> new UserInformation(
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getDate("date_of_birth"),
                            rs.getString("phone_number"),
                            rs.getString("email"),
                            rs.getString("pesel_number")
                    )
            );
        } catch (Exception e) {
            throw new IllegalStateException("User not found");
        }
    }

    public Users updateUser(Users user, Long userId) {
        try {
            StringBuilder sql = new StringBuilder("UPDATE users SET ");
            List<Object> params = new ArrayList<>();
            boolean isFirst = true;

            if (user.getName() != null) {
                if (!isFirst) sql.append(", ");
                sql.append("name = ?");
                params.add(user.getName());
                isFirst = false;
            }
            if (user.getSurname() != null) {
                if (!isFirst) sql.append(", ");
                sql.append("surname = ?");
                params.add(user.getSurname());
                isFirst = false;
            }
            if (user.getPhone_number() != null) {
                if (!isFirst) sql.append(", ");
                sql.append("phone_number = ?");
                params.add(user.getPhone_number());
                isFirst = false;
            }
            if (user.getEmail() != null) {
                if (!isFirst) sql.append(", ");
                sql.append("email = ?");
                params.add(user.getEmail());
                isFirst = false;
            }

            sql.append(" WHERE user_id = ?");
            params.add(userId);

            jdbcTemplate.update(sql.toString(), params.toArray());

            return user;
        } catch (Exception e) {
            throw new IllegalStateException("Error updating user", e);
        }
    }

    public Map<String, Integer> getPrediction(Map<String, Object> symptoms, UserDetailsDto userDetailsDto) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        TriageReport triageReport = new TriageReport();
        triageReport.setFacility_id((Integer) symptoms.get("facility_id"));
        symptoms.remove("facility_id");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(symptoms, headers);

        ResponseEntity<Map> response = restTemplate.exchange(PREDICT_URL, HttpMethod.POST, request, Map.class);

        triageReport.setUser_id(userDetailsDto.getUser_id());
        triageReport.setDate(LocalDateTime.now());
        triageReport.setTriage_colour((Integer) response.getBody().get("prediction"));

        jdbcTemplate.update("INSERT INTO triage_report (user_id, date, triage_colour, facility_id) VALUES (?, ?, ?, ?)",
                triageReport.getUser_id(),
                triageReport.getDate(),
                triageReport.getTriage_colour(),
                triageReport.getFacility_id()
        );

        return response.getBody();
    }


}

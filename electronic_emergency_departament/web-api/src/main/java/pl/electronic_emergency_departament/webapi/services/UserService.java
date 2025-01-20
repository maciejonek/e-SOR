package pl.electronic_emergency_departament.webapi.services;

import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.electronic_emergency_departament.emd_data.model.*;
import pl.electronic_emergency_departament.emd_data.repositories.TriageModelRepository;
import pl.electronic_emergency_departament.emd_data.repositories.TriageReportRepository;
import pl.electronic_emergency_departament.emd_data.repositories.UserRepository;
import pl.electronic_emergency_departament.webapi.registration.token.ConfirmationToken;
import pl.electronic_emergency_departament.webapi.registration.token.ConfirmationTokenService;
import pl.electronic_emergency_departament.webapi.services.security.model.UserDetailsDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final TriageReportRepository triageReportRepository;
    private TriageModelRepository triageModelRepository;

    private JdbcTemplate jdbcTemplate;

    private String PREDICT_URL = "http://3.120.206.66:80/predict";

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService, TriageReportRepository triageReportRepository, TriageModelRepository triageModelRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
        this.triageReportRepository = triageReportRepository;
        this.triageModelRepository = triageModelRepository;
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
    System.out.println("user_id: " + userId); // Log the data

    try {
        Users user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("User not found"));

        // Calculate age
        LocalDate birthDate = user.getDate_of_birth();
        int age = Period.between(birthDate, LocalDate.now()).getYears();

        UserInformation userInformation = new UserInformation(
            user.getName(),
            user.getSurname(),
            user.getDate_of_birth(),
            user.getPhone_number(),
            user.getEmail(),
            user.getPesel_number(),
            user.getSex(),
            age
        );

        System.out.println("Retrieved user data: " + userInformation); // Log the data
        return userInformation;
    } catch (Exception e) {
        System.out.println("Retrieved user data: NOT FOUND"); // Log the data
        throw new IllegalStateException("User not found");
    }
}

    public String getUserName(Long userId) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT name from users WHERE user_id = ?",
                    new Object[]{userId},
                    (rs, rowNum) -> rs.getString("name")
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

    public Map<String, Integer> getPrediction(Map<String, Object> symptoms, Long userId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        int facilityId = (int) symptoms.get("facility_id");
        int age = (int) symptoms.get("age");
        int n_surgeries = (int) symptoms.get("n_surgeries");

        symptoms.remove("facility_id");

        // Logowanie wysyłanych danych
        System.out.println("Wysyłane dane do PREDICT_URL: " + symptoms);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(symptoms, headers);
        ResponseEntity<Map> response;

        try {
            response = restTemplate.exchange(PREDICT_URL, HttpMethod.POST, request, Map.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Błąd podczas wywoływania PREDICT_URL: " + e.getMessage(), e);
        }

        System.out.println("Otrzymana odpowiedź z PREDICT_URL: " + response.getBody());

        // Sprawdź czy odpowiedź zawiera klucz "prediction"
        if (!response.getBody().containsKey("prediction")) {
            throw new IllegalArgumentException("Odpowiedź z PREDICT_URL nie zawiera klucza 'prediction'");
        }

        // Przetwarzanie odpowiedzi
        Map<String, Integer> prediction = new HashMap<>();
        prediction.put("prediction", (Integer) response.getBody().get("prediction"));

        // Logowanie predykcji
        System.out.println("Zapis predykcji: " + prediction);

        // Prepare the list of symptoms
        List<TriageModel> symptomList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : symptoms.entrySet()) {
            if (entry.getValue() instanceof Integer && (Integer) entry.getValue() == 1) {
                TriageModel symptom = triageModelRepository.findBySymptom(entry.getKey());
                if (symptom != null) {
                    symptomList.add(symptom);
                }
            }
        }

        // Save the TriageReport with symptoms
        TriageReport triageReport = new TriageReport(userId, LocalDateTime.now(), prediction.get("prediction"), facilityId, symptomList);
        triageReportRepository.save(triageReport);

        return prediction;
    }


    public Map<String, Object> getQueueDetails(Long userId) {
        int facilityId;
        try {
            // Znalezienie najnowszego `facilityId` dla użytkownika
            facilityId = triageReportRepository.findMostRecentFacilityIdByUserId(userId);
        } catch (Exception e) {
            throw new IllegalStateException("User is not assigned to any facility.");
        }

        // Pobranie wszystkich raportów dla wybranej placówki
        List<TriageReport> reports = triageReportRepository.findByFacilityId(facilityId);

        // Usuwanie raportów, których czas oczekiwania minął
        LocalDateTime currentTime = LocalDateTime.now();
        reports.removeIf(report -> {
            LocalDateTime estimatedTime = calculateEstimatedTimeForReport(report);
            return currentTime.isAfter(estimatedTime);
        });

        // Sortowanie raportów po `triage_colour` (priorytet) i `date` (starsze pierwsze)
        reports.sort((report1, report2) -> {
            int colourComparison = Integer.compare(report1.getTriage_colour(), report2.getTriage_colour());
            if (colourComparison == 0) {
                return report1.getDate().compareTo(report2.getDate());
            }
            return colourComparison;
        });

        // Usunięcie raportu użytkownika z bazy
        triageReportRepository.deleteByUserIdAndFacilityId(userId, facilityId); // Dodano usuwanie użytkownika

        // Pozycja użytkownika w kolejce
        int userPosition = -1;
        LocalDateTime latestDate = null;

        for (int i = 0; i < reports.size(); i++) {
            TriageReport report = reports.get(i);

            // Sprawdzenie, czy to raport tego samego użytkownika
            if (report.getUser_id().equals(userId)) {
                // Porównanie daty, aby znaleźć najnowszy raport
                if (latestDate == null || report.getDate().isAfter(latestDate)) {
                    latestDate = report.getDate();
                    userPosition = i + 1; // Ustawienie pozycji na podstawie najnowszej daty
                }
            }
        }

        if (userPosition == -1) {
            throw new IllegalStateException("User is not in the queue.");
        }

        // Obliczenie oczekiwanego czasu przyjęcia
        int userColour = reports.get(userPosition - 1).getTriage_colour();
        String estimatedTime = calculateEstimatedTime(reports, userPosition, userColour);

        // Przygotowanie odpowiedzi w formacie JSON
        Map<String, Object> response = new HashMap<>();
        response.put("totalQueueSize", reports.size());
        response.put("userPosition", userPosition);
        response.put("estimatedTime", estimatedTime);

        return response;
    }


    private LocalDateTime calculateEstimatedTimeForReport(TriageReport report) {
        int timeInMinutes = getTimeForColour(report.getTriage_colour());
        return report.getDate().plusMinutes(timeInMinutes);
    }

    private int getTimeForColour(int colour) {
        // Zwracaj czas dla poszczególnych kolorów
        return switch (colour) {
            case 1 -> 0;   // Czerwony - natychmiastowy
            case 2 -> 10;  // Pomarańczowy - 10 minut
            case 3 -> 60;  // Żółty - 60 minut
            case 4 -> 120; // Zielony - 120 minut
            case 5 -> 240; // Niebieski - 240 minut
            default -> 0;
        };
    }


    private String calculateEstimatedTime(List<TriageReport> reports, int userPosition, int userColour) {
        int timeInMinutes = 0;
        Map<Integer, Integer> priorityTimes = Map.of(
                1, 0,    // Czerwony - natychmiastowy
                2, 10,   // Pomarańczowy - 10 minut
                3, 60,   // Żółty - 60 minut
                4, 120,  // Zielony - 120 minut
                5, 240   // Niebieski - 240 minut
        );

        for (int i = 0; i < userPosition - 1; i++) {
            int colour = reports.get(i).getTriage_colour();
            timeInMinutes += priorityTimes.getOrDefault(colour, 0);
        }

        timeInMinutes += priorityTimes.getOrDefault(userColour, 0);

        // Obliczanie czasu w formacie daty
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime estimatedTime = currentTime.plusMinutes(timeInMinutes);

        // Formatowanie daty i czasu
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = estimatedTime.format(formatter);

        return String.format("%s (%d minut)", formattedTime, timeInMinutes);
    }


}

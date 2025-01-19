package pl.electronic_emergency_departament.webapi.tools;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.electronic_emergency_departament.emd_data.model.TriageColour;
import pl.electronic_emergency_departament.emd_data.repositories.TriageRepository;

@Configuration
public class DataInitializationConfig {

    @Bean
    CommandLineRunner initDatabase(TriageRepository repository) {
        return args -> {
            repository.save(new TriageColour("Czerwony"));
            repository.save(new TriageColour("Pomarańczowy"));
            repository.save(new TriageColour("Żółty"));
            repository.save(new TriageColour("Zielony"));
            repository.save(new TriageColour("Niebieski"));
        };
    }
}
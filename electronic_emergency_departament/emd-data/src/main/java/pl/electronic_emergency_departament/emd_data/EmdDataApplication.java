package pl.electronic_emergency_departament.emd_data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class EmdDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmdDataApplication.class, args);
    }

}

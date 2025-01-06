package pl.electronic_emergency_departament.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "pl.electronic_emergency_departament")
@EnableJpaRepositories(basePackages = {
    "pl.electronic_emergency_departament.emd_data.repositories",
    "pl.electronic_emergency_departament.webapi.registration.token"
})
@EntityScan(basePackages = {
    "pl.electronic_emergency_departament.emd_data.model",
    "pl.electronic_emergency_departament.webapi.registration.token"
})
public class WebApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApiApplication.class, args);
    }
}
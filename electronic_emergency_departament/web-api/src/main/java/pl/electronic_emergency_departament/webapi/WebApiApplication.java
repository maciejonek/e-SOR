package pl.electronic_emergency_departament.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "pl.electronic_emergency_department.emd_data.repositories")
@EntityScan(basePackages = "pl.electronic_emergency_department.emd_data.model")
public class WebApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApiApplication.class, args);
    }
}

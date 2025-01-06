package pl.electronic_emergency_departament.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "pl.electronic_emergency_departament")
//@EnableJpaRepositories(basePackages = {
//        "pl.electronic_emergency_departament.emd_data.repositories"
//})
@EntityScan(basePackages = {
        "pl.electronic_emergency_departament.emd_data.model"
})

public class WebApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApiApplication.class, args);
    }

}

package pl.electronic_emergency_departament.webapi.tools;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.electronic_emergency_departament.emd_data.model.Facilities;
import pl.electronic_emergency_departament.emd_data.repositories.FacilitiesRepository;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component

public class CsvImporter {
    @Value("classpath:szpitalne_oddziały_ratunkowe.csv")
    private org.springframework.core.io.Resource csvFile;

    private FacilitiesRepository repository;

    public CsvImporter(FacilitiesRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void importCsvToDatabase() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream(), StandardCharsets.UTF_8))) {
            // Pomijamy nagłówek
            reader.lines().skip(1).forEach(line -> {
                String[] columns = line.split(";");
                if (columns.length >= 6) {
                    Facilities sor = new Facilities();
                    sor.setEmail(columns[0]);
                    sor.setNazwaJednostki(columns[1]);
                    sor.setWojewodztwo(columns[2]);
                    sor.setMiejscowosc(columns[3]);
                    sor.setKodPocztowy(columns[4]);
                    sor.setAdres(columns[5]);
                    repository.save(sor);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

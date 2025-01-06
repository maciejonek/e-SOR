package pl.electronic_emergency_departament.webapi.registration;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {

    @Override
    public boolean test(String s){
        return true;
    }
}

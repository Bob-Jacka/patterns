package ru.netology.delivery.dataclass;

import com.github.javafaker.Faker;
import com.github.javafaker.service.RandomService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Generate {
    public static Faker fake = new Faker(new Locale("ru-RU"),
            new RandomService());

    public static String getName() {
        return fake.name().firstName() + " " + fake.name().lastName();
    }

    public static String getPhone() {
        return fake.phoneNumber().toString();
    }

    public static String getCity() {
        return fake.address().cityName();
    }

    public static String generateFromCurrDateForward(long toPlusDays) {
        return (LocalDate.now().plusDays(toPlusDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

    public static String generateFromCurrDateBackward(long toMinusDays) {
        return (LocalDate.now().minusDays(toMinusDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

}

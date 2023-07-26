package ru.netology.dataclass;

import com.github.javafaker.Faker;
import com.github.javafaker.service.RandomService;

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
}

package ru.netology.delivery.dataclass;

import com.github.javafaker.Faker;
import com.github.javafaker.service.RandomService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;

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

    public static String generatePlusDate(long toPlusDays) {
        return (LocalDate.now().plusDays(toPlusDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

    public static String generateMinusDate(long toMinusDays) {
        return (LocalDate.now().minusDays(toMinusDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

    public static void orderCard(String city, String name, String phone) {
        $("[data-test-id='city'] .input__control").sendKeys(city);
        $("[data-test-id='name'] .input__control").sendKeys(name);
        $("[data-test-id='phone'] .input__control").setValue(phone);
        $("[data-test-id='agreement']").click();
        $("[type='button'].button").click();
    }
}

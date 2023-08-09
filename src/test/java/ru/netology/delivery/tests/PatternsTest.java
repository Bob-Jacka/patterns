package ru.netology.delivery.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.delivery.dataclass.Generate.*;


public class PatternsTest {
    private final String site = "http://localhost:9999/";
    private final int validGap = 3;

    public static void orderCard(String city, String name, String phone, String date) {
        $("[data-test-id='city'] .input__control").sendKeys(city);
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").sendKeys(date);
        $("[data-test-id='name'] .input__control").sendKeys(name);
        $("[data-test-id='phone'] .input__control").setValue(phone);
        $("[data-test-id='agreement']").click();
        $("[type='button'].button").click();
    }

    @BeforeAll
    public static void setupAllure() {
        SelenideLogger.addListener("Allure", new AllureSelenide());
    }

    @AfterAll
    public static void removeAllure() {
        SelenideLogger.removeListener("Allure");
    }

    @Test
    public void shouldReplaneMeetWithSameData() {
        open(site);
        String city = getCity();
        String name = getName();
        String phone = getPhone();
        String date = generateFromCurrDateForward(validGap);

        orderCard(city, name, phone, date);
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.partialText("Встреча успешно запланирована на " + date),
                Duration.ofSeconds(10));

        refresh();

        orderCard(city, name, phone, date);

        $("[data-test-id='replan-notification']").shouldBe(Condition.visible, Duration.ofSeconds(10));
        $("[data-test-id='replan-notification'] button").click();
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.partialText("Встреча успешно запланирована на " + date),
                Duration.ofSeconds(10));
    }

    @Test
    public void shouldNotAskToReplaneMeetWithNotSameData() {
        open(site);

        String city = getCity();
        String name = getName();
        String phone = getPhone();
        String date = generateFromCurrDateForward(validGap);

        orderCard(city, name, phone, date);

        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.partialText("Встреча успешно запланирована на " + date),
                Duration.ofSeconds(10));

        refresh();

        String city2 = getCity();
        String name2 = getName();
        String phone2 = getPhone();
        String date2 = generateFromCurrDateForward(validGap + 1);

        orderCard(city2, name2, phone2, date2);

        $("[data-test-id='replan-notification']").shouldBe(Condition.hidden, Duration.ofSeconds(10));
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.partialText("Встреча успешно запланирована на " + date2),
                Duration.ofSeconds(10));
        $("[data-test-id='replan-notification']").shouldBe(Condition.hidden);
    }

    @Test
    public void canReplaneToTomorrow() {
        open(site);
        String tomorrowDate = generateFromCurrDateBackward(1);
        String city = getCity();
        String name = getName();
        String phone = getPhone();
        String date = generateFromCurrDateForward(validGap);

        orderCard(city, name, phone, date);
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.partialText("Встреча успешно запланирована на " + date),
                Duration.ofSeconds(10));

        $("[type='button'].button").click();

        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").sendKeys(tomorrowDate);

        $("[data-test-id='replan-notification'] button").click();

        $("[data-test-id='success-notification']").shouldBe(Condition.visible, Duration.ofSeconds(10));
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.partialText("Встреча успешно запланирована на " + tomorrowDate),
                Duration.ofSeconds(10));
    }

    @Test
    public void shouldReplaneWithAnotherName() {
        open(site);
        String anotherName = getName();
        String city = getCity();
        String name = getName();
        String phone = getPhone();
        String date = generateFromCurrDateForward(validGap);

        orderCard(city, name, phone, date);
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.partialText("Встреча успешно запланирована на " + date),
                Duration.ofSeconds(10));

        $("[type='button'].button").click();

        $("[data-test-id='name'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='name'] .input__control").sendKeys(anotherName);

        $("[data-test-id='replan-notification'] button").click();

        $("[data-test-id='success-notification']").shouldBe(Condition.visible, Duration.ofSeconds(10));
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.partialText("Встреча успешно запланирована на " + date),
                Duration.ofSeconds(10));
    }

    @Test
    public void shouldReplaneWithAnotherCity() {
        open(site);
        String newCity = getCity();
        String city = getCity();
        String name = getName();
        String phone = getPhone();
        String date = generateFromCurrDateForward(validGap);

        orderCard(city, name, phone, date);
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.partialText("Встреча успешно запланирована на " + date),
                Duration.ofSeconds(10));
        $("[type='button'].button").click();

        $("[data-test-id='city'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='city'] .input__control").sendKeys(newCity);

        $("[data-test-id='replan-notification'] button").click();

        $("[data-test-id='success-notification']").shouldBe(Condition.visible, Duration.ofSeconds(10));
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.partialText("Встреча успешно запланирована на " + date),
                Duration.ofSeconds(10));
    }

    @Test
    public void shouldReplaneWithAnotherPhone() {
        open(site);
        String newPhone = getPhone();
        String city = getCity();
        String name = getName();
        String phone = getPhone();
        String date = generateFromCurrDateForward(validGap);

        orderCard(city, name, phone, date);
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.partialText("Встреча успешно запланирована на " + date),
                Duration.ofSeconds(10));

        $("[type='button'].button").click();

        $("[data-test-id='phone'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='phone'] .input__control").sendKeys(newPhone);

        $("[data-test-id='replan-notification'] button").click();

        $("[data-test-id='success-notification']").shouldBe(Condition.visible, Duration.ofSeconds(10));
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.partialText("Встреча успешно запланирована на " + date),
                Duration.ofSeconds(10));
    }

    @Test
    public void shouldReplaneWithAnotherDate() {
        open(site);
        String newDate = generateFromCurrDateForward(validGap + 2);
        String city = getCity();
        String name = getName();
        String phone = getPhone();
        String date = generateFromCurrDateForward(validGap);

        orderCard(city, name, phone, date);
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.partialText("Встреча успешно запланирована на " + date),
                Duration.ofSeconds(10));

        $("[type='button'].button").click();

        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").sendKeys(newDate);

        $("[data-test-id='replan-notification'] button").click();

        $("[data-test-id='success-notification']").shouldBe(Condition.visible, Duration.ofSeconds(10));
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.partialText("Встреча успешно запланирована на " + newDate),
                Duration.ofSeconds(10));
    }
}

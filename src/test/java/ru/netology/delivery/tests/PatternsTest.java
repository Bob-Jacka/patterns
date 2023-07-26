package ru.netology.delivery.tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.dataclass.Generate;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.delivery.dataclass.Generate.orderCard;

public class PatternsTest {
    private final String site = "http://localhost:9999/";

    @Test
    public void shouldReplaneMeetWithSameData() {
        open(site);
        String city = Generate.getCity();
        String name = Generate.getName();
        String phone = Generate.getPhone();

        orderCard(city, name, phone);
        String date1 = $("[data-test-id='date'] .input__control").getValue();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(Condition.partialText(date1)).toString();

        refresh();

        orderCard(city, name, phone);

        $("[data-test-id='replan-notification']").shouldBe(Condition.visible, Duration.ofSeconds(10));
        $("[data-test-id='replan-notification'] .notification__content").shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"),
                Duration.ofSeconds(10));

        $("[data-test-id='replan-notification'] button").click();
        String date2 = $("[data-test-id='date'] .input__control").getValue();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(Condition.partialText(date2)).toString();
    }

    @Test
    public void shouldNotAskToReplaneMeetWithNotSameData() {
        open(site);

        String city = Generate.getCity();
        String name = Generate.getName();
        String phone = Generate.getPhone();
        orderCard(city, name, phone);

        String date1 = $("[data-test-id='date'] .input__control").getValue();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(Condition.partialText(date1)).toString();

        refresh();

        String city2 = Generate.getCity();
        String name2 = Generate.getName();
        String phone2 = Generate.getPhone();
        orderCard(city2, name2, phone2);

        $("[data-test-id='replan-notification']").shouldBe(Condition.hidden, Duration.ofSeconds(10));
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.partialText("Встреча успешно запланирована на "),
                Duration.ofSeconds(10));
        $("[data-test-id='replan-notification']").shouldBe(Condition.hidden);
        String date2 = $("[data-test-id='date'] .input__control").getValue();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(Condition.partialText(date2)).toString();
    }

    @Test
    public void canReplaneToTomorrow() {
        open(site);
        String tomorrowDate = Generate.generateMinusDate(1);
        String city = Generate.getCity();
        String name = Generate.getName();
        String phone = Generate.getPhone();

        orderCard(city, name, phone);
        String date1 = $("[data-test-id='date'] .input__control").getValue();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(Condition.partialText(date1)).toString();

        $("[type='button'].button").click();

        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").sendKeys(tomorrowDate);

        $("[data-test-id='replan-notification'] button").click();

        $("[data-test-id='success-notification']").shouldBe(Condition.visible, Duration.ofSeconds(10));
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.partialText("Встреча успешно запланирована на "),
                Duration.ofSeconds(10));
        String date2 = $("[data-test-id='date'] .input__control").getValue();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(Condition.partialText(date2)).toString();
    }

    @Test
    public void canReplaneWithAnotherName() {
        open(site);
        String anotherName = Generate.getName();
        String city = Generate.getCity();
        String name = Generate.getName();
        String phone = Generate.getPhone();

        orderCard(city, name, phone);
        String date1 = $("[data-test-id='date'] .input__control").getValue();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(Condition.partialText(date1));

        $("[type='button'].button").click();

        $("[data-test-id='name'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='name'] .input__control").sendKeys(anotherName);

        $("[data-test-id='replan-notification'] button").click();

        $("[data-test-id='success-notification']").shouldBe(Condition.visible, Duration.ofSeconds(10));
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.partialText("Встреча успешно запланирована на "),
                Duration.ofSeconds(10));

        String date2 = $("[data-test-id='date'] .input__control").getValue();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(Condition.partialText(date2));
    }

    @Test
    public void canReplaneWithAnotherCity() {
        open(site);
        String newCity = Generate.getCity();
        String city = Generate.getCity();
        String name = Generate.getName();
        String phone = Generate.getPhone();

        orderCard(city, name, phone);
        String date1 = $("[data-test-id='date'] .input__control").getValue();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(Condition.partialText(date1));
        $("[type='button'].button").click();

        $("[data-test-id='city'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='city'] .input__control").sendKeys(newCity);

        $("[data-test-id='replan-notification'] button").click();

        $("[data-test-id='success-notification']").shouldBe(Condition.visible, Duration.ofSeconds(10));
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.partialText("Встреча успешно запланирована на "),
                Duration.ofSeconds(10));

        String date2 = $("[data-test-id='date'] .input__control").getValue();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(Condition.partialText(date2));
    }

    @Test
    public void canReplaneWithAnotherPhone() {
        open(site);
        String newPhone = Generate.getPhone();
        String city = Generate.getCity();
        String name = Generate.getName();
        String phone = Generate.getPhone();

        orderCard(city, name, phone);
        String date1 = $("[data-test-id='date'] .input__control").getValue();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(Condition.partialText(date1));

        $("[type='button'].button").click();

        $("[data-test-id='phone'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='phone'] .input__control").sendKeys(newPhone);

        $("[data-test-id='replan-notification'] button").click();

        $("[data-test-id='success-notification']").shouldBe(Condition.visible, Duration.ofSeconds(10));
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.partialText("Встреча успешно запланирована на "),
                Duration.ofSeconds(10));
        String date2 = $("[data-test-id='date'] .input__control").getValue();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(Condition.partialText(date2));
    }

    @Test
    public void canReplaneWithAnotherDateForward() {
        open(site);
        String newDate = Generate.generatePlusDate(5);
        String city = Generate.getCity();
        String name = Generate.getName();
        String phone = Generate.getPhone();

        orderCard(city, name, phone);
        String date1 = $("[data-test-id='date'] .input__control").getValue();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(Condition.partialText(date1));

        $("[type='button'].button").click();

        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").sendKeys(newDate);

        $("[data-test-id='replan-notification'] button").click();

        $("[data-test-id='success-notification']").shouldBe(Condition.visible, Duration.ofSeconds(10));
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.partialText("Встреча успешно запланирована на "),
                Duration.ofSeconds(10));
        String date2 = $("[data-test-id='date'] .input__control").getValue();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(Condition.partialText(date2));
    }

}

package ru.netology.patterns.test;

import com.codeborne.selenide.selector.ByText;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.patterns.data.DataGenerator;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryDateChangeTests {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should success plan and replan meeting")
    void shouldSuccessPlanAndReplanMeeting()  {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForMeeting = 4;
        var firstDateOfMeeting = DataGenerator.generateDate(daysToAddForMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondDateOfMeeting = DataGenerator.generateDate(daysToAddForSecondMeeting);


        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE );
        $("[data-test-id='date'] input").setValue(firstDateOfMeeting);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='success-notification']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Успешно!"))
                .shouldHave(text("Встреча успешно запланирована на " + firstDateOfMeeting));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE );
        $("[data-test-id='date'] input").setValue(secondDateOfMeeting);
        $(byText("Запланировать")).click();
        $("[data-test-id='replan-notification']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Необходимо подтверждение"));
        $$(".button__text").find(text("Перепланировать")).click();
        $("[data-test-id='success-notification']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Успешно!"))
                .shouldHave(text("Встреча успешно запланирована на " + secondDateOfMeeting));
    }
}

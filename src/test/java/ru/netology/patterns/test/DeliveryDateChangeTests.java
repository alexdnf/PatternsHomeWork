package ru.netology.patterns.test;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.patterns.data.DataGenerator;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryDateChangeTests {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should success plan and replan meeting")
    void shouldSuccessPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser();
        var daysToAddForMeeting = 4;
        var firstDateOfMeeting = DataGenerator.generateDate(daysToAddForMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondDateOfMeeting = DataGenerator.generateDate(daysToAddForSecondMeeting);

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstDateOfMeeting);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='success-notification']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Успешно!"))
                .shouldHave(text("Встреча успешно запланирована на " + firstDateOfMeeting));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
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

    @Test
    @DisplayName("Should send form with letter 'ё'")
    void shouldSendCorrectForm() {
        var validUser = DataGenerator.Registration.generateUser();
        var daysToAddForMeeting = 4;
        var firstDateOfMeeting = DataGenerator.generateDate(daysToAddForMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondDateOfMeeting = DataGenerator.generateDate(daysToAddForSecondMeeting);


        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstDateOfMeeting);
        $("[data-test-id='name'] input").setValue("Артём Иванов");
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='success-notification']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Успешно!"))
                .shouldHave(text("Встреча успешно запланирована на " + firstDateOfMeeting));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
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

    @Test
    @DisplayName("Should not send form with wrong city")
    void wrongCityTest() {
        var validUser = DataGenerator.Registration.generateUser();
        var daysToAddForMeeting = 4;
        var firstDateOfMeeting = DataGenerator.generateDate(daysToAddForMeeting);

        $("[data-test-id=city] input").setValue(validUser.getCity() + " ово");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstDateOfMeeting);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    @DisplayName("Should not send form with empty city")
    void wrongEmptyCityTest() {
        var validUser = DataGenerator.Registration.generateUser();
        var daysToAddForMeeting = 4;
        var firstDateOfMeeting = DataGenerator.generateDate(daysToAddForMeeting);

        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstDateOfMeeting);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));

    }
    @Test
    @DisplayName("Should not send form with wrong city latin letters")
    void wrongLatinCityTest() {
        var validUser = DataGenerator.Registration.generateUser();
        var daysToAddForMeeting = 4;
        var firstDateOfMeeting = DataGenerator.generateDate(daysToAddForMeeting);
        Faker faker = new Faker(new Locale("en"));

        $("[data-test-id=city] input").setValue(faker.address().cityName());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstDateOfMeeting);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    @DisplayName("Should not send form with wrong city numbers")
    void wrongNumbersCityTest() {
        var validUser = DataGenerator.Registration.generateUser();
        var daysToAddForMeeting = 4;
        var firstDateOfMeeting = DataGenerator.generateDate(daysToAddForMeeting);

        $("[data-test-id=city] input").setValue(validUser.getCity() + "123");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstDateOfMeeting);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    @DisplayName("Should not send form with wrong city symbols")
    void wrongSymbolsCityTest() {
        var validUser = DataGenerator.Registration.generateUser();
        var daysToAddForMeeting = 4;
        var firstDateOfMeeting = DataGenerator.generateDate(daysToAddForMeeting);

        $("[data-test-id=city] input").setValue(validUser.getCity() + "??");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstDateOfMeeting);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    @DisplayName("Should not send form with wrong today date")
    void wrongTodayDateTest() {
        var validUser = DataGenerator.Registration.generateUser();
        var daysToAddForMeeting = 0;
        var firstDateOfMeeting = DataGenerator.generateDate(daysToAddForMeeting);

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstDateOfMeeting);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='date'] .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }
    @Test
    @DisplayName("Should not send form with wrong tomorrow date")
    void wrongTomorrowDateTest() {
        var validUser = DataGenerator.Registration.generateUser();
        var daysToAddForMeeting = 1;
        var firstDateOfMeeting = DataGenerator.generateDate(daysToAddForMeeting);

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstDateOfMeeting);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='date'] .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }
    @Test
    @DisplayName("Should not send form with wrong 2 days after today date")
    void wrong2DaysDateTest() {
        var validUser = DataGenerator.Registration.generateUser();
        var daysToAddForMeeting = 2;
        var firstDateOfMeeting = DataGenerator.generateDate(daysToAddForMeeting);

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstDateOfMeeting);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='date'] .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }
    @Test
    @DisplayName("Should not send form with wrong late date")
    void wrongLateDateTest() {
        var validUser = DataGenerator.Registration.generateUser();
        var daysToAddForMeeting = -1;
        var firstDateOfMeeting = DataGenerator.generateDate(daysToAddForMeeting);

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstDateOfMeeting);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='date'] .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    @DisplayName("Should not send form with wrong name latin letters")
    void wrongLatinNameTest() {
        var validUser = DataGenerator.Registration.generateUser();
        var daysToAddForMeeting = 4;
        var firstDateOfMeeting = DataGenerator.generateDate(daysToAddForMeeting);
        Faker faker = new Faker(new Locale("en"));

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstDateOfMeeting);
        $("[data-test-id='name'] input").setValue(faker.name().fullName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    @DisplayName("Should not send form with wrong name numbers")
    void wrongNumbersNameTest() {
        var validUser = DataGenerator.Registration.generateUser();
        var daysToAddForMeeting = 4;
        var firstDateOfMeeting = DataGenerator.generateDate(daysToAddForMeeting);

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstDateOfMeeting);
        $("[data-test-id='name'] input").setValue(validUser.getName() + "123");
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    @DisplayName("Should not send form with wrong name numbers")
    void wrongSymbolsNameTest() {
        var validUser = DataGenerator.Registration.generateUser();
        var daysToAddForMeeting = 4;
        var firstDateOfMeeting = DataGenerator.generateDate(daysToAddForMeeting);

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstDateOfMeeting);
        $("[data-test-id='name'] input").setValue(validUser.getName() + "??");
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    @DisplayName("Should not send form with empty name")
    void wrongEmptyNameTest() {
        var validUser = DataGenerator.Registration.generateUser();
        var daysToAddForMeeting = 4;
        var firstDateOfMeeting = DataGenerator.generateDate(daysToAddForMeeting);

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstDateOfMeeting);
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Should not send form with wrong empty phone number")
    void wrongEmptyPhoneNumberTest() {
        var validUser = DataGenerator.Registration.generateUser();
        var daysToAddForMeeting = 4;
        var firstDateOfMeeting = DataGenerator.generateDate(daysToAddForMeeting);

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstDateOfMeeting);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
}

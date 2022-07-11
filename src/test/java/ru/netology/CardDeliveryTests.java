package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class CardDeliveryTests {

    public String generateDate(int shift) {
        String date;
        LocalDate localDate = LocalDate.now().plusDays(shift);
        date = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(localDate);
        return date;
    }

    @BeforeEach
    void openUrl() {
        open("http://localhost:9999");
    }


    @AfterEach
    void tearDown() {
        closeWindow();
    }


    @Test
    void shouldTestSuccessIfCorrectFilling() {
        String date = generateDate(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(Keys.chord(BACK_SPACE,
                        date));
        $("[data-test-id='name'] .input__control").setValue("Анатолий Иванов-Полищук");
        $("[data-test-id='phone'] .input__control").setValue("+79995558877");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Встреча успешно забронирована"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(withText("Встреча успешно забронирована"))
                .shouldBe(Condition.text(date));
    }

    @Test
    void shouldTestSuccessOrderIfPlus365Days() {
        String date = generateDate(365);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(Keys.chord(BACK_SPACE,
                        date));
        $("[data-test-id='name'] .input__control").setValue("Анатолий Иванов-Полищук");
        $("[data-test-id='phone'] .input__control").setValue("+79995558877");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Встреча успешно забронирована"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(withText("Встреча успешно забронирована"))
                .shouldBe(Condition.text(date));
    }

    @Test
    void shouldTestFailingIfPlus2Days() {
        String date = generateDate(2);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(Keys.chord(BACK_SPACE,
                        date));
        $("[data-test-id='name'] .input__control").setValue("Анатолий Иванов-Полищук");
        $("[data-test-id='phone'] .input__control").setValue("+79995558877");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Заказ на выбранную дату невозможен"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldTestFailingIfNoName() {
        String date = generateDate(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(Keys.chord(BACK_SPACE,
                        date));
        $("[data-test-id='phone'] .input__control").setValue("+79995558877");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldTestFailingIfNoCity() {
        String date = generateDate(3);
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(Keys.chord(BACK_SPACE,
                        date));
        $("[data-test-id='name'] .input__control").setValue("Анатолий Иванов-Полищук");
        $("[data-test-id='phone'] .input__control").setValue("+79995558877");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldTestFailingIfNoNumber() {
        String date = generateDate(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(Keys.chord(BACK_SPACE,
                        date));
        $("[data-test-id='name'] .input__control").setValue("Анатолий Иванов-Полищук");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldTestFailingIfNoDate() {
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(Keys.chord(BACK_SPACE));
        $("[data-test-id='name'] .input__control").setValue("Анатолий Иванов-Полищук");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Неверно введена дата"))
                .shouldBe(Condition.visible);
    }


    @Test
    void shouldTestFailingIfNameInEnglish() {
        String date = generateDate(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(Keys.chord(BACK_SPACE,
                        date));
        $("[data-test-id='name'] .input__control").setValue("Anatoliy");
        $("[data-test-id='phone'] .input__control").setValue("+79995558877");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("только русские буквы, пробелы и дефисы"))
                .shouldBe(Condition.visible);
    }


    @Test
    void shouldTestFailingIfTelIncorrect() {
        String date = generateDate(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(Keys.chord(BACK_SPACE,
                        date));
        $("[data-test-id='name'] .input__control").setValue("Анатолий Иванов-Полищук");
        $("[data-test-id='phone'] .input__control").setValue("+7999555887766");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Телефон указан неверно"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldTestFailingIfNoClickOnCofirmation() {
        String date = generateDate(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(Keys.chord(BACK_SPACE,
                        date));
        $("[data-test-id='name'] .input__control").setValue("Анатолий Иванов-Полищук");
        $("[data-test-id='phone'] .input__control").setValue("+79995558877");
        $$("button").find(Condition.exactText("Забронировать")).click();
        String text = $(".checkbox__text").getCssValue("color");
        assertEquals("rgba(255, 92, 92, 1)", text);
    }


}


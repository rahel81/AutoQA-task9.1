package ru.netology.delivery;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldSendValidValue() {
        $("[data-test-id='city'] input").setValue(CardDeliveryGenerator.getCity());
        SelenideElement data = $("[data-test-id=date]");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        data.$("[placeholder]").setValue(CardDeliveryGenerator.getData(5));
        $("[data-test-id=name] input").setValue(CardDeliveryGenerator.getName());
        $("[data-test-id=phone] input").setValue(CardDeliveryGenerator.getPhone());
        $("[data-test-id=agreement]").click();
        $$("[type=button]").find(text("Запланировать")).click();
        $("[data-test-id=success-notification]").$("[class=notification__content]").waitUntil(visible, 15000)
                .shouldHave(text("Встреча успешно запланирована на " + CardDeliveryGenerator.getData(5)));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        data.$("[placeholder]").setValue(CardDeliveryGenerator.getData(6));
        $$("[type=button]").find(text("Запланировать")).click();
        $(withText("Необходимо подтверждение")).waitUntil(visible, 15000);
        $$("[type=button]").find(text("Перепланировать")).click();
        $("[data-test-id=success-notification]").$("[class=notification__content]").waitUntil(visible, 15000)
                .shouldHave(text("Встреча успешно запланирована на " + CardDeliveryGenerator.getData(6)));
    }

    @Test
    void shouldSendInvalidCity() {
        $("[data-test-id='city'] input").setValue(CardDeliveryGenerator.getCityInvalid());
        SelenideElement data = $("[data-test-id=date]");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        data.$("[placeholder]").setValue(CardDeliveryGenerator.getData(5));
        $("[data-test-id=name] input").setValue(CardDeliveryGenerator.getName());
        $("[data-test-id=phone] input").setValue(CardDeliveryGenerator.getPhone());
        $("[data-test-id=agreement]").click();
        $$("[type=button]").find(text("Запланировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").waitUntil(visible, 15000)
                .$(withText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldSendInvalidData() {
        $("[data-test-id='city'] input").setValue(CardDeliveryGenerator.getCity());
        SelenideElement data = $("[data-test-id=date]");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").doubleClick().sendKeys("10.03.2021");
        $("[data-test-id=name] input").setValue(CardDeliveryGenerator.getName());
        $("[data-test-id=phone] input").setValue(CardDeliveryGenerator.getPhone());
        $("[data-test-id=agreement]").click();
        $$("[type=button]").find(text("Запланировать")).click();
        $("[data-test-id='date'].input_invalid.input__sub")
                .$(withText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldSendInvalidName() {
        $("[data-test-id='city'] input").setValue(CardDeliveryGenerator.getCity());
        SelenideElement data = $("[data-test-id=date]");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        data.$("[placeholder]").setValue(CardDeliveryGenerator.getData(5));
        $("[data-test-id=name] input").setValue("Elena");
        $("[data-test-id=phone] input").setValue(CardDeliveryGenerator.getPhone());
        $("[data-test-id=agreement]").click();
        $$("[type=button]").find(text("Запланировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").waitUntil(visible, 15000)
                .$(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldSendInvalidPhone() {
        $("[data-test-id='city'] input").setValue(CardDeliveryGenerator.getCity());
        SelenideElement data = $("[data-test-id=date]");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        data.$("[placeholder]").setValue(CardDeliveryGenerator.getData(5));
        $("[data-test-id=name] input").setValue(CardDeliveryGenerator.getName());
        $("[data-test-id=phone] input").setValue(CardDeliveryGenerator.getInvalidPhone());
        $("[data-test-id=agreement]").click();
        $$("[type=button]").find(text("Запланировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").waitUntil(visible, 15000)
                .$(withText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }


    @Test
    void shouldSendInvalidCheckbox() {
        $("[data-test-id='city'] input").setValue(CardDeliveryGenerator.getCity());
        SelenideElement data = $("[data-test-id=date]");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        data.$("[placeholder]").setValue(CardDeliveryGenerator.getData(5));
        $("[data-test-id=name] input").setValue(CardDeliveryGenerator.getName());
        $("[data-test-id=phone] input").setValue(CardDeliveryGenerator.getPhone());
        $$("[type=button]").find(text("Запланировать")).click();
        $("[data-test-id=agreement].input_invalid").waitUntil(visible, 15000)
                .$(withText("Я соглашаюсь с условиями обработки и использования моих персональных данных " +
                        "и разрешаю сделать запрос в бюро кредитных историй"));
    }

    @Test
    void shouldSendEmptyFieldCity() {
        $("[data-test-id='city'] input").setValue("");
        SelenideElement data = $("[data-test-id=date]");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        data.$("[placeholder]").setValue(CardDeliveryGenerator.getData(5));
        $("[data-test-id=name] input").setValue(CardDeliveryGenerator.getName());
        $("[data-test-id=phone] input").setValue(CardDeliveryGenerator.getPhone());
        $("[data-test-id=agreement]").click();
        $$("[type=button]").find(text("Запланировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").waitUntil(visible, 15000)
                .$(withText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendEmptyFieldName() {
        $("[data-test-id='city'] input").setValue(CardDeliveryGenerator.getCity());
        SelenideElement data = $("[data-test-id=date]");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        data.$("[placeholder]").setValue(CardDeliveryGenerator.getData(5));
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue(CardDeliveryGenerator.getPhone());
        $("[data-test-id=agreement]").click();
        $$("[type=button]").find(text("Запланировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").waitUntil(visible, 15000)
                .$(withText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendEmptyFieldPhone() {
        $("[data-test-id='city'] input").setValue(CardDeliveryGenerator.getCity());
        SelenideElement data = $("[data-test-id=date]");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        data.$("[placeholder]").setValue(CardDeliveryGenerator.getData(5));
        $("[data-test-id=name] input").setValue(CardDeliveryGenerator.getName());
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $$("[type=button]").find(text("Запланировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").waitUntil(visible, 15000)
                .$(withText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendEmptyFieldData() {
        $("[data-test-id='city'] input").setValue(CardDeliveryGenerator.getCity());
        SelenideElement data = $("[data-test-id=date]");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").doubleClick().sendKeys("");
        $("[data-test-id=name] input").setValue(CardDeliveryGenerator.getName());
        $("[data-test-id=phone] input").setValue(CardDeliveryGenerator.getPhone());
        $("[data-test-id=agreement]").click();
        $$("[type=button]").find(text("Запланировать")).click();
        $("[data-test-id='date'].input_invalid.input__sub")
                .$(withText("Неверно введена дата"));
    }
}

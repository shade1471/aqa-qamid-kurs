package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.val;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class OrderPage {
    private SelenideElement buyButton = $(byText("Купить"));
    private SelenideElement headingBuy = $$(".heading")
            .findBy(Condition.exactText("Оплата по карте"));

    private SelenideElement headingBuyCredit = $$(".heading")
            .findBy(Condition.exactText("Кредит по данным карты"));

    private SelenideElement buyCreditButton = $(byText("Купить в кредит"));
    private SelenideElement proceedButton = $(byText("Продолжить"));

    private SelenideElement numberCard = $$(".input__inner")
            .findBy(Condition.exactText("Номер карты")).$("input");
    private SelenideElement monthField = $$(".input__inner")
            .findBy(Condition.exactText("Месяц")).$("input");
    private SelenideElement yearField = $$(".input__inner")
            .findBy(Condition.exactText("Год")).$("input");
    private SelenideElement ownerField = $$(".input__inner")
            .findBy(Condition.exactText("Владелец")).$("input");
    private SelenideElement codeField = $$(".input__inner")
            .findBy(Condition.exactText("CVC/CVV")).$("input");
    private SelenideElement statusOk = $(".notification_status_ok .notification__title");
    private SelenideElement statusError = $(".notification_status_error .notification__title");
    private SelenideElement wrongFormat = $(".input_invalid .input__inner .input__sub");

    private ElementsCollection description = $$(".list__item");
    private final String amountStart = "Всего ";
    private final String amountFinish = " руб.!";

    @Step("Ввод данных в форму при оплате по дебетовой карте")
    public void dataInputDebt(DataHelper.OrderInfo info) {
        buyButton.click();
        headingBuy.shouldBe(visible);
        numberCard.setValue(info.getNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        ownerField.setValue(info.getOwner());
        codeField.setValue(info.getCvc());
        proceedButton.click();
    }

    @Step("Отправка формы по одобренной дебетовой карте")
    public void orderByApproved(DataHelper.OrderInfo info) {
        dataInputDebt(info);
        statusOk.shouldHave(text("Успешно"), Duration.ofSeconds(20));
    }

    @Step("Отправка формы по отклоненной дебетовой карте")
    public void orderByDeclined(DataHelper.OrderInfo info) {
        dataInputDebt(info);
        statusError.shouldHave(text("Ошибка"), Duration.ofSeconds(20));
    }

    @Step("Отправка формы по не валидной дебетовой карте")
    public void orderByNotValid(DataHelper.OrderInfo info) {
        dataInputDebt(info);
        statusError.shouldHave(text("Ошибка"), Duration.ofSeconds(20));
    }

    public void clearField(SelenideElement field) {
        field.sendKeys(Keys.CONTROL + "A");
        field.sendKeys(Keys.BACK_SPACE);
    }

    @Step("Ввод данных в форму и проверка поля 'Номер карты' со значением {number}")
    public void checkNumberField(String number, DataHelper.OrderInfo info) {
        buyButton.click();
        numberCard.setValue(number);
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        ownerField.setValue(info.getOwner());
        codeField.setValue(info.getCvc());
        proceedButton.click();
        wrongFormat.shouldHave(text("Неверный формат"));
    }

    @Step("Ввод данных в форму и проверка поля 'Месяц карты' со значением {month}")
    public void checkMonthField(String month, DataHelper.OrderInfo info) {
        buyButton.click();
        numberCard.setValue(info.getNumber());
        monthField.setValue(month);
        yearField.setValue(info.getYear());
        ownerField.setValue(info.getOwner());
        codeField.setValue(info.getCvc());
        proceedButton.click();
        wrongFormat.shouldHave(or("formatOrValidity", text("Неверный формат"),
                text("Неверно указан срок действия карты")));
    }

    @Step("Ввод данных в форму и проверка поля 'Год карты' со значением {year}")
    public void checkYearField(String year, DataHelper.OrderInfo info) {
        buyButton.click();
        numberCard.setValue(info.getNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(year);
        ownerField.setValue(info.getOwner());
        codeField.setValue(info.getCvc());
        proceedButton.click();
        wrongFormat.shouldHave(or("formatOrValidity", text("Неверный формат"), ownText("срок действия карты")));
    }

    @Step("Ввод данных в форму и проверка поля 'Владелец карты' со значением {owner}")
    public void checkOwnerField(String owner, DataHelper.OrderInfo info) {
        buyButton.click();
        numberCard.setValue(info.getNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        ownerField.setValue(owner);
        codeField.setValue(info.getCvc());
        proceedButton.click();
        wrongFormat.shouldHave(or("formatOrValid", text("Поле обязательно для заполнения"), text("Неверный формат")));
    }

    @Step("Вводы данных в форму и проверка поля CVC/CVV со значением {cvc}")
    public void checkCvcField(String cvc, DataHelper.OrderInfo info) {
        buyButton.click();
        numberCard.setValue(info.getNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        ownerField.setValue(info.getOwner());
        codeField.setValue(cvc);
        proceedButton.click();
        wrongFormat.shouldHave(text("Неверный формат"));
    }

    @Step("Получение стоимости путешествия со страницы заказа")
    public int getTravelPrice() {
        val text = description.findBy(ownText(amountFinish)).text();
        return extractAmount(text);
    }

    private int extractAmount(String text) {
        val start = text.indexOf(amountStart);
        val finish = text.indexOf(amountFinish);
        val value = text.substring(start + amountStart.length(), finish).replace(" ", "");
        return Integer.parseInt(value);
    }
}

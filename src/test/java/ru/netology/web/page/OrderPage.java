package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
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

    public void dataInput(DataHelper.OrderInfo info) {
        numberCard.setValue(info.getNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        ownerField.setValue(info.getOwner());
        codeField.setValue(info.getCvc());
        proceedButton.click();
    }

    public void validOrderByApprovedDebtCard(DataHelper.OrderInfo info) {
        buyButton.click();
        headingBuy.shouldBe(visible);
        dataInput(info);
        statusOk.shouldHave(text("Успешно"), Duration.ofSeconds(15));
    }

    public void validOrderByDeclinedDebtCard(DataHelper.OrderInfo info) {
        buyButton.click();
        headingBuy.shouldBe(visible);
        dataInput(info);
        statusError.shouldHave(text("Ошибка"), Duration.ofSeconds(15));
    }

    public void notValidOrderByDebtCard(DataHelper.OrderInfo info) {
        buyButton.click();
        headingBuy.shouldBe(visible);
        dataInput(info);
        statusError.shouldHave(text("Ошибка"), Duration.ofSeconds(15));
    }

    public void clearField(SelenideElement field) {
        field.sendKeys(Keys.CONTROL + "A");
        field.sendKeys(Keys.BACK_SPACE);
    }

    public void numberFieldCheck(String number, DataHelper.OrderInfo info) {
        buyButton.click();
        numberCard.setValue(number);
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        ownerField.setValue(info.getOwner());
        codeField.setValue(info.getCvc());
        proceedButton.click();
        wrongFormat.shouldHave(text("Неверный формат"));
    }

    public void monthFieldCheck(String month, DataHelper.OrderInfo info) {
        buyButton.click();
        numberCard.setValue(info.getNumber());
        monthField.setValue(month);
        yearField.setValue(info.getYear());
        ownerField.setValue(info.getOwner());
        codeField.setValue(info.getCvc());
        proceedButton.click();
        //wrongFormat.shouldBe(Condition.visible);
        wrongFormat.shouldHave(or("formatOrValidity", text("Неверный формат"),
                text("Неверно указан срок действия карты")));
    }

    public void yearFieldCheck(String year, DataHelper.OrderInfo info) {
        buyButton.click();
        numberCard.setValue(info.getNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(year);
        ownerField.setValue(info.getOwner());
        codeField.setValue(info.getCvc());
        proceedButton.click();
        //wrongFormat.shouldBe(Condition.visible);
        wrongFormat.shouldHave(or("formatOrValidity", text("Неверный формат"), ownText("срок действия карты")));
    }

    public void ownerFieldCheck(String owner, DataHelper.OrderInfo info) {
        buyButton.click();
        numberCard.setValue(info.getNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        ownerField.setValue(owner);
        codeField.setValue(info.getCvc());
        proceedButton.click();
        wrongFormat.shouldHave(text("Поле обязательно для заполнения"));
    }
}

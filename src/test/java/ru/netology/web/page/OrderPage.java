package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

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
    private SelenideElement wrongFormat = $(".input__inner .input__sub");

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
        headingBuy.shouldBe(Condition.visible);
        dataInput(info);
        statusOk.shouldHave(Condition.text("Успешно"), Duration.ofSeconds(15));
    }

    public void validOrderByDeclinedDebtCard(DataHelper.OrderInfo info) {
        buyButton.click();
        headingBuy.shouldBe(Condition.visible);
        dataInput(info);
        statusError.shouldHave(Condition.text("Ошибка"), Duration.ofSeconds(15));
    }

    public void notValidOrderByDebtCard(DataHelper.OrderInfo info) {
        buyButton.click();
        headingBuy.shouldBe(Condition.visible);
        dataInput(info);
        statusError.shouldHave(Condition.text("Ошибка"), Duration.ofSeconds(15));
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
        wrongFormat.shouldHave(Condition.text("Неверный формат"));
    }
}

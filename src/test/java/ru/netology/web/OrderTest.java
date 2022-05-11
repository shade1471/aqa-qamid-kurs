package ru.netology.web;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.OrderPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.data.DataHelper.OrderInfo.*;

public class OrderTest {
    private DataHelper.OrderInfo infoApproved = getValidInfoApproved();
    private DataHelper.OrderInfo infoDeclined = getValidInfoDeclined();

    private DataHelper.OrderInfo infoNotValid = getNotValidInfo();
    private OrderPage orderPage = new OrderPage();

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
        Configuration.holdBrowserOpen = true;
    }

    @Test
    void shouldSendOrderByDebtCardApproved() {
        orderPage.validOrderByApprovedDebtCard(infoApproved);
    }

    @Test
    void shouldSendOrderByDebtCardDeclined() {
        orderPage.validOrderByDeclinedDebtCard(infoDeclined);
    }

    @Test
    void shouldSendOrderByDebtCardNotValid() {
        orderPage.notValidOrderByDebtCard(infoNotValid);
    }

    @Test
    void shouldCheckNumberFieldWith12Digits() {
        orderPage.numberFieldCheck("1111 2222 3333", infoApproved);
    }

    @Test
    void shouldCheckNumberFieldWith15Digits() {
        orderPage.numberFieldCheck("1111 2222 3333 444", infoApproved);
    }

    @Test
    void shouldCheckNumberFieldWith1Digit() {
        orderPage.numberFieldCheck("1", infoApproved);
    }

    @Test
    void shouldCheckNumberFieldEmpty() {
        orderPage.numberFieldCheck("", infoApproved);
    }

    @Test
    void shouldCheckMonthFieldEmpty() {
        orderPage.monthFieldCheck("", infoApproved);
    }

    @Test
    void shouldCheckMonthFieldWith1Digit() {
        orderPage.monthFieldCheck("5", infoApproved);
    }

    @Test
    void shouldCheckMonthFieldLessThanBoundaryValues() {
        orderPage.monthFieldCheck("00", infoApproved);
    }

    @Test
    void shouldCheckMonthFieldMoreThanBoundaryValues() {
        orderPage.monthFieldCheck("13", infoApproved);
    }

    @Test
    void shouldCheckYearFieldEmpty() {
        orderPage.yearFieldCheck("", infoApproved);
    }

    @Test
    void shouldCheckYearFieldWith1Digit() {
        orderPage.yearFieldCheck("2", infoApproved);
    }

    @Test
    void shouldCheckYearFieldValueLessThanCurrentYear() {
        orderPage.yearFieldCheck(getLastYear(1), infoApproved);
    }

    @Test
    void shouldCheckYearFieldValueMoreThanFiveYears() {
        orderPage.yearFieldCheck(getFutureYear(6), infoApproved);
    }

    @Test
    void shouldCheckOwnerFieldEmpty() {
        orderPage.ownerFieldCheck("", infoApproved);
    }



}

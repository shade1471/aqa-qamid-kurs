package ru.netology.web;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.OrderPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.data.DataHelper.OrderInfo.*;

public class WebFormTest {

    private DataHelper.OrderInfo infoApproved = getValidInfoApproved();

    private OrderPage orderPage = new OrderPage();

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
        Configuration.holdBrowserOpen = true;
    }

    @Test
    void shouldCheckNumberFieldWith12Digits() {
        orderPage.checkNumberField("1111 2222 3333", infoApproved);
    }

    @Test
    void shouldCheckNumberFieldWith15Digits() {
        orderPage.checkNumberField("1111 2222 3333 444", infoApproved);
    }

    @Test
    void shouldCheckNumberFieldWith1Digit() {
        orderPage.checkNumberField("1", infoApproved);
    }

    @Test
    void shouldCheckNumberFieldEmpty() {
        orderPage.checkNumberField("", infoApproved);
    }

    @Test
    void shouldCheckMonthFieldEmpty() {
        orderPage.checkMonthField("", infoApproved);
    }

    @Test
    void shouldCheckMonthFieldWith1Digit() {
        orderPage.checkMonthField("5", infoApproved);
    }

    @Test
    void shouldCheckMonthFieldLessThanBoundaryValues() {
        orderPage.checkMonthField("00", infoApproved);
    }

    @Test
    void shouldCheckMonthFieldMoreThanBoundaryValues() {
        orderPage.checkMonthField("13", infoApproved);
    }

    @Test
    void shouldCheckMonthFieldNotValidValues() {
        orderPage.checkMonthField("24", infoApproved);
    }

    @Test
    void shouldCheckYearFieldEmpty() {
        orderPage.checkYearField("", infoApproved);
    }

    @Test
    void shouldCheckYearFieldWith1Digit() {
        orderPage.checkYearField("2", infoApproved);
    }

    @Test
    void shouldCheckYearFieldValueLessThanCurrentYear() {
        orderPage.checkYearField(getManualYear(-1), infoApproved);
    }

    @Test
    void shouldCheckYearFieldValueMoreThanFiveYears() {
        orderPage.checkYearField(getManualYear(6), infoApproved);
    }

    @Test
    void shouldCheckOwnerFieldEmpty() {
        orderPage.checkOwnerField("", infoApproved);
    }

    @Test
    void shouldCheckCvcFieldEmpty() {
        orderPage.checkCvcField("", infoApproved);
    }

    @Test
    void shouldCheckCvcFieldWith1Digit() {
        orderPage.checkCvcField("1", infoApproved);
    }
}

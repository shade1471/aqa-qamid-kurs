package ru.netology.web;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.OrderPage;
import ru.netology.web.utils.SQLRequest;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.OrderInfo.*;

public class OrderTest {
    private DataHelper.OrderInfo infoApproved = getValidInfoApproved();
    private DataHelper.OrderInfo infoDeclined = getValidInfoDeclined();

    private DataHelper.OrderInfo infoNotValid = getNotValidInfo();
    private OrderPage orderPage = new OrderPage();

    @Nested
    public class Order {
        @BeforeEach
        void setup() {
            open("http://localhost:8080");
            // Configuration.holdBrowserOpen = true;
        }

        @Test
        void shouldSendOrderByDebtCardApproved() {
            orderPage.orderByApproved(infoApproved);
        }

        @Test
        void shouldSendOrderByDebtCardDeclined() {
            orderPage.orderByDeclined(infoDeclined);
        }

        @Test
        void shouldSendOrderByDebtCardNotValid() {
            orderPage.orderByNotValid(infoNotValid);
        }
    }

    @Nested
    public class WebForm {
        @BeforeEach
        void setup() {
            open("http://localhost:8080");
            //Configuration.holdBrowserOpen = true;
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

    @Nested
    public class SQL {

        @BeforeEach
        void setup() {
            open("http://localhost:8080");
            //Configuration.holdBrowserOpen = true;
        }

        @Test
        void shouldCheckAmount() {
            orderPage.orderByApproved(infoApproved);
            int actual = SQLRequest.getAmount();
            int expected = orderPage.getTravelPrice();
            assertEquals(expected, actual);
        }

        @Test
        void shouldCheckStatusIfCardApproved() {
            orderPage.orderByApproved(infoApproved);
            String actual = SQLRequest.getStatus();
            assertEquals("APPROVED", actual);
        }

        @Test
        void shouldCheckStatusIfCardDeclined() {
            orderPage.orderByApproved(infoDeclined);
            String actual = SQLRequest.getStatus();
            assertEquals("DECLINED", actual);
        }

    }
}

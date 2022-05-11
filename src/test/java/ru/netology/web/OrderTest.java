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

package ru.netology.web;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.OrderPage;
import ru.netology.web.utils.ApiRequest;
import ru.netology.web.utils.SQLRequest;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.OrderInfo.*;

public class OrderTest {
    private DataHelper.OrderInfo infoApproved = getValidInfoApproved();
    private DataHelper.OrderInfo infoDeclined = getValidInfoDeclined();

    private DataHelper.OrderInfo infoNotValid = getNotValidInfo();
    private OrderPage orderPage = new OrderPage();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Nested
    public class Order {
        @Step(value = "Открытие страницы")
        @BeforeEach
        void setup() {
            open("http://localhost:8080");
            // Configuration.holdBrowserOpen = true;
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Оформление заказа по одобренной карте")
        @Severity(value = SeverityLevel.CRITICAL)
        @Test
        void shouldSendOrderByDebtCardApproved() {
            orderPage.orderByApproved(infoApproved);
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Оформление заказа по отклоненной карте")
        @Severity(value = SeverityLevel.CRITICAL)
        @Test
        void shouldSendOrderByDebtCardDeclined() {
            orderPage.orderByDeclined(infoDeclined);
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Оформление заказа по не валидной карте")
        @Severity(value = SeverityLevel.CRITICAL)
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

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Проверка поля веб формы, номер карты: 12-ти значное число")
        @Test
        void shouldCheckNumberFieldWith12Digits() {
            orderPage.checkNumberField("1111 2222 3333", infoApproved);
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Проверка поля веб формы, номер карты: 15-ти значное число")
        @Test
        void shouldCheckNumberFieldWith15Digits() {
            orderPage.checkNumberField("1111 2222 3333 444", infoApproved);
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Проверка поля веб формы, номер карты: Однозначное число")
        @Test
        void shouldCheckNumberFieldWith1Digit() {
            orderPage.checkNumberField("1", infoApproved);
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Проверка поля веб формы, номер карты: пустое поле")
        @Test
        void shouldCheckNumberFieldEmpty() {
            orderPage.checkNumberField("", infoApproved);
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Проверка поля веб формы, месяц карты: Пустое поле")
        @Test
        void shouldCheckMonthFieldEmpty() {
            orderPage.checkMonthField("", infoApproved);
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Проверка поля веб формы, месяц карты: Однозначное число")
        @Test
        void shouldCheckMonthFieldWith1Digit() {
            orderPage.checkMonthField("5", infoApproved);
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Проверка поля веб формы, месяц карты: Граничное значение '00' ")
        @Test
        void shouldCheckMonthFieldLessThanBoundaryValues() {
            orderPage.checkMonthField("00", infoApproved);
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Проверка поля веб формы, месяц карты: Граничное значение '13'")
        @Test
        void shouldCheckMonthFieldMoreThanBoundaryValues() {
            orderPage.checkMonthField("13", infoApproved);
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Проверка поля веб формы, месяц карты: Не валидное число '24'")
        @Test
        void shouldCheckMonthFieldNotValidValues() {
            orderPage.checkMonthField("24", infoApproved);
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Проверка поля веб формы, год карты: Пустое поле")
        @Test
        void shouldCheckYearFieldEmpty() {
            orderPage.checkYearField("", infoApproved);
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Проверка поля веб формы, год карты: Однозначное число")
        @Test
        void shouldCheckYearFieldWith1Digit() {
            orderPage.checkYearField("2", infoApproved);
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Проверка поля веб формы, год карты: Прошедший год")
        @Test
        void shouldCheckYearFieldValueLessThanCurrentYear() {
            orderPage.checkYearField(getManualYear(-1), infoApproved);
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Проверка поля веб формы, год карты: Срок действия карты больше 5 лет")
        @Test
        void shouldCheckYearFieldValueMoreThanFiveYears() {
            orderPage.checkYearField(getManualYear(6), infoApproved);
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Проверка поля веб формы, владелец карты: Пустое поле")
        @Test
        void shouldCheckOwnerFieldEmpty() {
            orderPage.checkOwnerField("", infoApproved);
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Проверка поля веб формы, владелец карты: Кириллические символы")
        @Test
        void shouldCheckOwnerFieldWithCyrillicSymbols() {
            orderPage.checkOwnerField("Иван Иванов", infoApproved);
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Проверка поля веб формы, CVC/CVV: Пустое поле")
        @Test
        void shouldCheckCvcFieldEmpty() {
            orderPage.checkCvcField("", infoApproved);
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Проверка поля веб формы, CVC/CVV: Однозначное число")
        @Test
        void shouldCheckCvcFieldWith1Digit() {
            orderPage.checkCvcField("1", infoApproved);
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Проверка поля веб формы, CVC/CVV: Нижнее граничное значение")
        @Test
        void shouldCheckCvcFieldLowBoundary() {
            orderPage.checkCvcField("000", infoApproved);
        }
    }

    @Nested
    public class SQL {

        @BeforeEach
        void setup() {
            open("http://localhost:8080");
            //Configuration.holdBrowserOpen = true;
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Проверка записи в СУБД стоимости путешествия при оплате одобренной картой")
        @Severity(value = SeverityLevel.CRITICAL)
        @Test
        void shouldCheckAmount() {
            orderPage.orderByApproved(infoApproved);
            int actual = SQLRequest.getAmount();
            int expected = orderPage.getTravelPrice();
            assertEquals(expected, actual);
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Проверка записи в СУБД статуса операции при оплате одобренной картой")
        @Test
        void shouldCheckStatusIfCardApproved() {
            orderPage.orderByApproved(infoApproved);
            String actual = SQLRequest.getStatus();
            assertEquals("APPROVED", actual);
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "Функционал для заказа путешествия с выбором оплаты")
        @Story("Проверка записи в СУБД статуса операции при оплате отклоненной картой")
        @Test
        void shouldCheckStatusIfCardDeclined() {
            orderPage.orderByApproved(infoDeclined);
            String actual = SQLRequest.getStatus();
            assertEquals("DECLINED", actual);
        }

    }

    @Nested
    public class Api {

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "API запрос")
        @Story("Проверка статуса операции по API при оплате одобренной картой")
        @Test
        void shouldCheckStatusByApiWithApprovedCard() {
            String actual = ApiRequest.getStatus(infoApproved);
            assertEquals("APPROVED", actual);
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "API запрос")
        @Story("Проверка статуса операции по API при оплате отклоненной картой")
        @Test
        void shouldCheckStatusByApiWithDeclinedCard() {
            String actual = ApiRequest.getStatus(infoDeclined);
            assertEquals("DECLINED", actual);
        }

        @Epic(value = "Приложение для заказа путешествия")
        @Feature(value = "API запрос")
        @Story("Проверка статуса операции по API при оплате невалидной картой")
        @Test
        void shouldCheckStatusByApiWithNotValidCard() {
            ApiRequest.getStatusNotValid(infoNotValid);
        }

    }
}


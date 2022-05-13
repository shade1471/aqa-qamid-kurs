package ru.netology.web.utils;


import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.netology.web.data.DataHelper;

import static io.restassured.RestAssured.given;

public class ApiRequest {

    static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost/api/v1")
            .setPort(8080)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @Step("Получение статуса операции из ответа на API Запрос")
    public static String getStatus(DataHelper.OrderInfo info) {
        String status = given()
                .spec(requestSpec)// указываем, какую спецификацию используем
                .body(new Gson().toJson(info))
                .when() // "когда"
                .post("/pay") // на какой путь, относительно BaseUri отправляем запрос
                .then()
                .statusCode(200)
                .extract()
                .path("status");
        return status;
    }

    @Step("Проверка на 500 код, при запросе по API с данными по невалидной карте")
    public static void getStatusNotValid(DataHelper.OrderInfo info) {
        given()
                .spec(requestSpec)// указываем, какую спецификацию используем
                .body(new Gson().toJson(info))
                .when() // "когда"
                .post("/pay") // на какой путь, относительно BaseUri отправляем запрос
                .then()
                .statusCode(500);
    }
}


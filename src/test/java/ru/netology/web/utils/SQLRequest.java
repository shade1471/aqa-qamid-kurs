package ru.netology.web.utils;

import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLRequest {
    private static QueryRunner runner = new QueryRunner();

    @SneakyThrows
    public static Connection connection() {
        var conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "shade1471", "shade1471");
        return conn;
    }

    @Step("Запрос стоимости тура по последней транзакции")
    @SneakyThrows
    public static int getAmount(String query) {
        var amount = runner.query(connection(), query, new ScalarHandler<>()).toString();
        return Integer.parseInt(amount);
    }

    @Step("Запрос статуса последней транзакции")
    @SneakyThrows
    public static String getStatus(String query) {
        var status = runner.query(connection(), query, new ScalarHandler<>()).toString();
        return status;
    }

}

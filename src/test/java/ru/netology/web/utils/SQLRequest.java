package ru.netology.web.utils;

import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLRequest {

    public static String url = System.getProperty("db.url");
    public static String user = System.getProperty("db.user");
    public static String pass = System.getProperty("db.password");

    public static String amountSQL = "SELECT amount FROM payment_entity\n" +
            "ORDER BY created DESC \n" +
            "LIMIT 1;";

    public static String statusSQL = "SELECT status FROM payment_entity\n" +
            "ORDER BY created DESC \n" +
            "LIMIT 1;";
    private static QueryRunner runner = new QueryRunner();

    @SneakyThrows
    public static Connection connection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            conn = DriverManager.getConnection(System.getProperty("db.urlpost"), user, pass);
        }
        return conn;
    }

    @Step("Запрос стоимости тура по последней транзакции")
    @SneakyThrows
    public static int getAmount() {
        var amount = runner.query(connection(), amountSQL, new ScalarHandler<>()).toString();
        return Integer.parseInt(amount);
    }

    @Step("Запрос статуса последней транзакции")
    @SneakyThrows
    public static String getStatus() {
        var status = runner.query(connection(), statusSQL, new ScalarHandler<>()).toString();
        return status;
    }

}

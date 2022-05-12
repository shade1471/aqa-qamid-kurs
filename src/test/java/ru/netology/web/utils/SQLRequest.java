package ru.netology.web.utils;

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

    @SneakyThrows
    public static int getAmount() {
        var amountSQL = "SELECT amount FROM payment_entity\n" +
                "ORDER BY created DESC \n" +
                "LIMIT 1;";
        var amount = runner.query(connection(), amountSQL, new ScalarHandler<>()).toString();
        return Integer.parseInt(amount);
    }

    @SneakyThrows
    public static String getStatus() {
        var statusSQL = "SELECT status FROM payment_entity\n" +
                "ORDER BY created DESC \n" +
                "LIMIT 1;";
        var status = runner.query(connection(), statusSQL, new ScalarHandler<>()).toString();
        return status;
    }

}

package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataHelper {

    public DataHelper() {
    }

    @Value
    public static class OrderInfo {
        private String number;
        private String month;
        private String year;
        private String owner;
        private String cvc;

        public static OrderInfo getValidInfoApproved() {
            return new OrderInfo("1111 2222 3333 4444", getCurrentMonth(), generateYear(), generateOwner("en"), generateCvc());
        }

        public static OrderInfo getValidInfoDeclined() {
            return new OrderInfo("5555 6666 7777 8888", getCurrentMonth(), generateYear(), generateOwner("en"), generateCvc());
        }

        public static OrderInfo getNotValidInfo() {
            return new OrderInfo("1234 4321 5678 8765", getCurrentMonth(), generateYear(), generateOwner("en"), generateCvc());
        }

        public static String getCurrentMonth() {
            String currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
            return currentMonth;
        }

        public static String getLastYear(int shift) {
            int currentYear = LocalDate.now().minusYears(shift).getYear();
            return Integer.toString(currentYear).substring(2);
        }

        public static String getFutureYear(int shift) {
            int currentYear = LocalDate.now().plusYears(shift).getYear();
            return Integer.toString(currentYear).substring(2);
        }

        public static String generateYear() {
            int[] years = new int[6];
            for (int i = 0; i < 6; i++) {
                years[i] = LocalDate.now().plusYears(i).getYear();
            }
            int randYear = years[new Random().nextInt(years.length)];
            return Integer.toString(randYear).substring(2);
        }

        public static String generateOwner(String locale) {
            String owner = new Faker(new Locale(locale)).name().fullName();
            return owner.toUpperCase();
        }

        public static String generateCvc() {
            int cvc = new Random().nextInt(900) + 100;
            return Integer.toString(cvc);
        }
    }


}

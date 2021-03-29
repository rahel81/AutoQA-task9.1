package ru.netology.delivery;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardDeliveryGenerator {
    private CardDeliveryGenerator() {
    }

    static final Faker faker = new Faker(new Locale("ru"));

    public static String getCity() {
        Random random = new Random();
        String city[] = {"Великий Новгород", "Санкт-Петербург", "Уфа", "Казань"};
        int inputCity = random.nextInt(city.length);
        return city[inputCity];
    }

    public static String getCityInvalid() {
        Random random = new Random();
        String city[] = {"Луга", "Валдай", "Дербент"};
        int inputCity = random.nextInt(city.length);
        return city[inputCity];
    }

    public static String getData(int day) {
        String inputData = LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return inputData;
    }

    public static String getName() {
        String name = faker.name().lastName().replace("ё", "е");
        name = name + " " + faker.name().firstName().replace("ё", "е");
        return name;
    }

    public static String getPhone() {
        String phone = faker.phoneNumber().phoneNumber()
                .replace("+", "").replace("(", "")
                .replace(")", "").replace("-", "");
        return phone;
    }

    public static String getInvalidPhone() {
        String phone = faker.numerify("+7#########")
                .replace("+", "").replace("(", "")
                .replace(")", "").replace("-", "");
        return phone;
    }
}

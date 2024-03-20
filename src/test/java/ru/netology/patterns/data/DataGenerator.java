package ru.netology.patterns.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity(String local) {
        Faker faker = new Faker(new Locale("ru"));
        String[] cities = {
                "Москва", "Тверь", "Смоленкс", "Калуга", "Брянск",
                "Тула", "Орел", "Рязань", "Владимир", "Иваново",
                "Кострома", "Ярославль", "Курск",
                "Белгород", "Липецк", "Воронеж",
                "Тамбов", "Санкт-Петербург", "Великий Новгород",
                "Псков", "Вологда", "Петрозаводск",
                "Мурманск", "Архангельск", "Нарьян-Мар", "Сыктывкар",
                "Калининград", "Нижний Новгород", "Киров",
                "Йошкар-Ола", "Чебоксары", "Казань", "Саранск", "Ульяновск",
                "Пенза", "Саратов", "Самара", "Пермь", "Ижевск",
                "Уфа", "Оренбург", "Волгоград", "Астрахань", "Элиста", "Ростов-на-Дону", "Краснодар", "Майкоп",
                "Симферополь", "Ставрополь", "Черкесск", "Нальчик", "Владикавказ",
                "Магас", "Грозный", "Махачкала", "Екатеринбург", "Челябинск",
                "Курган", "Тюмень", "Ханты-Мансийск", "Салехард",
                "Омск", "Томск", "Новосибирск", "Кемерово", "Барнаул",
                "Горно-Алтайск", "Красноярск", "Абакан",
                "Кызыл", "Иркутск", "Улан-Удэ",
                "Чита", "Якутск", "Магадан", "Анадырь", "Петропавловск-Камчатский",
                "Благовещенск", "Биробиджан", "Хабаровск", "Владивосток", "Южно-Сахалинск"
        };

        Random random = new Random();
        int randomIndex = random.nextInt(cities.length);
        return cities[randomIndex];
    }

    public static String generateName(String local) {
        Faker faker = new Faker(new Locale("ru"));
        String firstName = faker.name().firstName();
        String surname = faker.name().lastName();

        if (firstName.contains("ё")) {
            firstName = firstName.replace("ё","е");
        }
        if (surname.contains("ё")) {
            surname = surname.replace("ё","е");
        }
        return firstName + " " + surname;
    }

    public static String generatePhone(String local) {
        Faker faker = new Faker(new Locale("ru"));
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        private Registration() {
        }
        public static UserInfo generateUser(String local) {
            Faker faker = new Faker(new Locale("ru"));
            return new UserInfo(
                    DataGenerator.generateCity("ru"),
                    DataGenerator.generateName("ru"),
                    DataGenerator.generatePhone("ru")
            );
        };

        @Value
        public static class UserInfo {
            String city;
            String name;
            String phone;
        }
    }
}

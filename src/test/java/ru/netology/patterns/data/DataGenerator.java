package ru.netology.patterns.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity() {
        List<String> cities = Arrays.asList(
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
        );

        Random random = new Random();
        int randomIndex = random.nextInt(cities.size());
        return cities.get(randomIndex);
    }

    public static String generateWrongCitySmallTown() {
        return generateCity() + "ово";
    }

    public static String generateWrongCityNumbers() {
        return generateCity() + "123";
    }

    public static String generateWrongCitySymbols() {
        return generateCity() + "???";
    }
    public static String generateWrongCityWithLatinLetters() {
        Faker faker = new Faker(new Locale("en"));
        return faker.address().cityName();
    }

    public static String generateName() {
        Faker faker = new Faker(new Locale("ru"));
        String firstName = faker.name().firstName();
        String surname = faker.name().lastName();

        return (surname + " " + firstName).replace("ё", "е");
    }

    public static String generateValidNameWithRusLetter() {
        Faker faker = new Faker(new Locale("ru"));
        String firstName = faker.name().firstName();
        String surname = faker.name().lastName();

        return surname + "ё" + " " + firstName;
    }

    public static String generateWrongNameWithNumbers() {
        return generateName() + "123";
    }

    public static String generateWrongNameWithSymbols() {
        return generateName() + "?/,-";
    }
    public static String generateWrongNameWithLatinLetters() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().fullName();
    }

    public static String generatePhone() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser() {
            return new UserInfo(
                    DataGenerator.generateCity(),
                    DataGenerator.generateName(),
                    DataGenerator.generatePhone()
            );
        }

    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}

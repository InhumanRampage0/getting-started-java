package data;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {

    private static final Faker faker = new Faker(new Locale("ru"));
    private static final Random rand = new Random();

    // Список реальных кодов регионов для России
    private static final List<String> regions = List.of(
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
            "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60",
            "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80",
            "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99"
    );

    // Генерация случайного ФИО на кириллице
    public static String generateFIO() {
        String firstName = faker.name().firstName(); // Имя
        String lastName = faker.name().lastName(); // Фамилия
        String patronymic = faker.name().lastName(); // Отчество (можно генерировать как фамилию)

        return lastName + " " + firstName + " " + patronymic;
    }

    // Метод для генерации случайной даты рождения в формате дд.мм.гггг
    public static String generateDateOfBirth() {
        // Генерация случайного возраста от 18 до 100 лет
        int age = rand.nextInt(83) + 18;  // Диапазон от 18 до 100 лет
        // Получаем текущую дату
        Date today = new Date();
        // Рассчитываем дату рождения, вычитаем количество лет из текущей даты
        long ageInMillis = today.getTime() - (age * 365L * 24 * 60 * 60 * 1000);
        Date birthDate = new Date(ageInMillis);

        // Форматируем дату в нужный формат (дд.мм.гггг)
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(birthDate);
    }

    // Генерация паспорта с учетом серии и номера
    public static String generatePassport() {
        // Генерация региона из списка
        String region = regions.get(rand.nextInt(regions.size()));

        // Генерация года выпуска
        String year = String.format("%02d", faker.number().numberBetween(10, 99));  // 10-99

        // Формируем серию без дефиса, например: 1508
        String series = region + year;

        // Генерация номера паспорта (6 случайных цифр)
        String number = String.format("%06d", faker.number().numberBetween(100000, 999999));

        // Формируем паспорт без пробела
        return series + number;
    }

    // Метод для генерации случайного СНИЛС
    public static String generateSnils() {
        // Генерация случайных 9 цифр
        String snils = faker.number().digits(9);

        // Вычисление контрольной суммы
        int[] weights = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(snils.charAt(i)) * weights[i];
        }

        int controlNumber = sum % 101;
        if (controlNumber == 100) {
            controlNumber = 0;
        }

        // Формирование корректного СНИЛС
        return snils + String.format("-%02d", controlNumber);
    }

    // Генерация случайного номера телефона (формат: 9782117868)
    public static String generatePhoneNumber() {
        // Генерация номера телефона в формате 9xxxxxxxxx
        return "9" + faker.number().digits(9);
    }

    public static void main(String[] args) {
        // Пример использования всех генераторов
        System.out.println("ФИО: " + generateFIO());
        System.out.println("Дата рождения: " + generateDateOfBirth());
        System.out.println("Паспорт: " + generatePassport());
        System.out.println("СНИЛС: " + generateSnils());
        System.out.println("Телефон: " + generatePhoneNumber());
    }
}

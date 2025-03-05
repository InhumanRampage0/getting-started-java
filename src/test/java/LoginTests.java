import com.codeborne.selenide.Configuration;
import data.DataGenerator;  // Импортируем общий класс для генерации данных
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class LoginTests {

    private static String snils;
    private static String passport;
    private static String fio; // Переменная для хранения сгенерированного ФИО
    private static String dateOfBirth; // Переменная для хранения сгенерированной даты рождения
    private static String phoneNumber; // Переменная для хранения сгенерированного номера телефона

    @BeforeAll
    static void beforeAll() {
        // Настройка конфигурации браузера
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://app-test16-3.myhrlink.ru";

        // Генерация данных для СНИЛС, паспорта, ФИО, даты рождения и номера телефона через DataGenerator
        snils = DataGenerator.generateSnils();  // Генерация СНИЛС
        passport = DataGenerator.generatePassport();  // Генерация паспорта
        fio = DataGenerator.generateFIO();  // Генерация случайного ФИО
        dateOfBirth = DataGenerator.generateDateOfBirth(); // Генерация случайной даты рождения
        phoneNumber = DataGenerator.generatePhoneNumber(); // Генерация случайного номера телефона
    }

    @BeforeEach
    void beforeEach() {
        open("/login");

        // Вводим логин и пароль
        $("input[data-qa='credential-form-login-input']").setValue("bpershko+7@hr-link.ru");
        $("input[data-qa='credential-form-password-input']").setValue("Qwerty12345!");
        $(".mat-button-wrapper").click();  // Кликаем на кнопку "Войти"

        // Явное ожидание загрузки страницы после входа
        $("[data-qa='hr-app-nav-employees-registry-link']")
                .shouldBe(visible, Duration.ofSeconds(15));

        // Закрываем всплывающее окно, если оно появляется
        $("[data-qa='telegram-invitation-dlg-cancel-button']")
                .shouldBe(visible, Duration.ofSeconds(10))
                .click();

        // Переходим в раздел сотрудников
        $("[data-qa='hr-app-nav-employees-registry-link']").click();

        // Переходим на страницу добавления нового сотрудника
        $("[data-qa='registry-top-menu-add-employee-link']")
                .shouldBe(visible, Duration.ofSeconds(10))
                .click();

        // Заполняем форму нового сотрудника с использованием сгенерированного ФИО
        String[] fioParts = fio.split(" "); // Разделяем ФИО на части (фамилия, имя, отчество)
        $("input[data-qa='person-full-name-last-name-input']").setValue(fioParts[0]); // Фамилия
        $("input[data-qa='person-full-name-first-name-input']").setValue(fioParts[1]); // Имя
        $("input[data-qa='person-full-name-patronymic-input']").setValue(fioParts[2]); // Отчество

        // Заполняем случайную дату рождения, сгенерированную методом
        $("input[data-qa='employee-new-card-date-of-birthday-input']").setValue(dateOfBirth);

        // Заполняем паспорт и СНИЛС из сгенерированных данных
        $("input[data-qa='passport-input-input']").setValue(passport);  // Используем сгенерированный паспорт
        $("input[data-qa='employee-new-card-snils-input']").setValue(snils);  // Используем сгенерированный СНИЛС

        // Выбираем пол
        $("[data-qa='employee-new-card-gender-select']").click();
        $("[data-qa='employee-new-card-gender-select-option-0']").click(); // Мужской
    }

    @Test
    void newEmployeeCreateTest() {
        $("[data-qa='employee-new-card-next-step-button']").click();
        // Вставляем сгенерированный номер телефона
        $("input[data-qa='phone-field-phone-digit-input']").setValue(phoneNumber);
        $("[data-qa='employee-new-card-next-step-button']").click();

        $("[data-qa='employee-new-card-legal-entity-select']").click();
        $("[data-qa='employee-new-card-gender-legal-entity-select-option-6']").click();

        $("[data-qa='employee-new-card-employee-roles-select']").click();
        $("[data-qa='employee-new-card-employee-roles-select-option-1']").click();

        $("[data-qa='employee-new-card-save-button']").click();
        sleep(10000);
        // Ваш тестовый код для создания нового сотрудника
    }
}

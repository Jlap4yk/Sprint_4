package ru.praktikum;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class OrderPageTest {
    private WebDriver driver;
    private final String pageUrl = MainPage.PAGE_URL;

    // Параметры для теста
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final String deliveryDate;
    private final String rentalPeriod;
    private final String color;
    private final String comment;
    private final boolean isTopButton;

    // Конструктор для параметризации
    public OrderPageTest(String firstName, String lastName, String address, String metroStation,
                         String phone, String deliveryDate, String rentalPeriod, String color,
                         String comment, boolean isTopButton) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.deliveryDate = deliveryDate;
        this.rentalPeriod = rentalPeriod;
        this.color = color;
        this.comment = comment;
        this.isTopButton = isTopButton;
    }

    // Два набора данных
    @Parameterized.Parameters()
    public static Object[][] testOrder() {
        return new Object[][]{
                // Набор 1 валидные значения
                {"Катя", "Попкова", "ул. Ленина, 101", "Черкизовская", "+79990909999", "15.04.2025", "сутки", "чёрный жемчуг", "Позвонить за час", true},
                // Набор 2 невалидные значения
                {"Катя", "Попкова", "ул. Ленина, 101", "Черкизовская", "79990909999", "15.04.2025", "сутки", "чёрный жемчуг", "Оставить у двери", false},
        };
    }

    @Before
    public void StartUp() {
        WebDriverManager.chromedriver().setup();

        // закомичен потому что проверяем через Мозилу, снять комит, поставить на фоксе что бы проверить на хроме.
        // driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        //driver.get(pageUrl);
        //WebDriverManager.firefoxdriver().setup();

        driver = new FirefoxDriver();
        driver.manage().window().maximize(); // Запуск в полноэкранном режиме
        driver.get(pageUrl);
    }




    @Test
    public void testOrdtestOrderFlowViaTopButtonerFlow() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        OrderPage orderPage = new OrderPage(driver);

        // Шаг 0: Принять куки
        mainPage.clickOnCookieButton();

        // Шаг 1: Нажать кнопку «Заказать» (верхняя)
        mainPage.clickOrderButtonTop();

        // Шаг 2: Ожидать загрузки формы
        orderPage.waitForLoadOrderPage();

        // Шаг 3: Заполнить форму «Для кого самокат»
        orderPage.setNameInput(firstName);
        orderPage.setSurnameInput(lastName);
        orderPage.setAddressInput(address);
        orderPage.setMetroInput(metroStation);
        orderPage.setPhoneInput(phone);

        // Шаг 4: Нажать «Далее»
        orderPage.clickButtonNext();

        // Шаг 5: Заполнить форму «Про аренду»
        orderPage.setDateInput(deliveryDate);
        orderPage.setTermInput(rentalPeriod);
        orderPage.setColorInput(color);
        orderPage.setCommentInput(comment);

        // Шаг 6: Завершить заказ
        orderPage.makeOrder();

        // Шаг 7: Проверить сообщение об успешном заказе
        String successMessage = orderPage.getOrderSuccessMessage();
        assertThat("Сообщение об успешном заказе не появилось или некорректно",
                successMessage, containsString("Заказ оформлен"));
    }

    @Test
    public void testOrderFlowViaBottomButton() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        OrderPage orderPage = new OrderPage(driver);

        // Шаг 0: Принять куки
        mainPage.clickOnCookieButton();

        // Шаг 1: Нажать кнопку «Заказать» (нижняя)
        mainPage.clickOrderButtonBottom();

        // Шаг 2: Ожидать загрузки формы
        orderPage.waitForLoadOrderPage();

        // Шаг 3: Заполнить форму «Для кого самокат»
        orderPage.setNameInput(firstName);
        orderPage.setSurnameInput(lastName);
        orderPage.setAddressInput(address);
        orderPage.setMetroInput(metroStation);
        orderPage.setPhoneInput(phone);

        // Шаг 4: Нажать «Далее»
        orderPage.clickButtonNext();

        // Шаг 5: Заполнить форму «Про аренду»
        orderPage.setDateInput(deliveryDate);
        orderPage.setTermInput(rentalPeriod);
        orderPage.setColorInput(color);
        orderPage.setCommentInput(comment);

        // Шаг 6: Завершить заказ
        orderPage.makeOrder();

        // Шаг 7: Проверить сообщение об успешном заказе
        String successMessage = orderPage.getOrderSuccessMessage();
        assertThat("Сообщение об успешном заказе не появилось или некорректно",
                successMessage, containsString("Заказ оформлен"));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
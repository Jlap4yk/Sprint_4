package ru.praktikum;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.praktikum.MainPage;

import java.time.Duration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.fail;
import static ru.praktikum.MainPage.PAGE_URL;

@RunWith(Parameterized.class)
public class MainPageTest {
    private WebDriver driver;
    private final String pageUrl = PAGE_URL;

    private final int numberOfElement;

    private final String expectedHeaderText;
    private final String expectedItemText;

    public MainPageTest(int numberOfAccordionItem, String expectedHeaderText, String expectedItemText) {
        numberOfElement = numberOfAccordionItem;
        this.expectedHeaderText = expectedHeaderText;
        this.expectedItemText = expectedItemText;
    }

    @Parameterized.Parameters(name = "Проверяемый элемент: {1}" )
    public static Object[][] testData() {
        return new Object[][]{
                {0, "Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Я живу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области."}, //BUG жизу != живу
        };
    }

    @Before
    public void StartUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(PAGE_URL);
        driver.manage().window().maximize();

        //driver = new FirefoxDriver();
        //driver.get(PAGE_URL);

     //   driver = new FirefoxDriver();
    //    driver.manage().window().maximize(); // Запуск в полноэкранном режиме
    //    driver.get(pageUrl);
    //    new WebDriverWait(driver, Duration.ofSeconds(10))
    //            .until(ExpectedConditions.urlToBe(PAGE_URL));
    }

    @Test
    public void checkAccordion() {
        MainPage mainPage = new MainPage(driver);

        mainPage.clickOnCookieButton();

        mainPage.clickHeaderAccordion(numberOfElement);
        mainPage.waitLoadAccordionItem(numberOfElement);

        if (mainPage.isAccordionItemOpen(numberOfElement)) {
            MatcherAssert.assertThat("Проблема с текстом в заголовке " + numberOfElement,
                    expectedHeaderText, equalTo(mainPage.getTextHeaderAccordion(numberOfElement)));
            MatcherAssert.assertThat("Проблема с текстом в теле " + numberOfElement,
                    expectedItemText, equalTo(mainPage.getTextItemAccordion(numberOfElement)));
        }
        else{
            fail("Элемент №" + numberOfElement + " не загрузился");
        }
    }

    @After
    public void tearDown() {

        driver.quit();
    }


}


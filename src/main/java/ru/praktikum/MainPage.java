package ru.praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    // Создали драйвер
    private WebDriver driver;

    // Локаторы на всей страницы сайта

    // локатор логотипа яндекс
    private final By clickLogoYandex = By.className("Header_LogoYandex__3TSOI");
    // локатор логотипа самокат
    private final By clickLogoScooter = By.className("Header_LogoScooter__3lsAR");
    // локатор кнопки куки
    private final By cookieButton = By.xpath(".//button[contains(@class, 'App_CookieButton__3cvqF') and @id='rcc-confirm-button' and contains(text(), 'да все привыкли')]");
    // локатор раскрывающего блока с вопросами
    private final By accordionHeader = By.className("accordion__button");
    // локатор раскрывающего блока
    private final By accordionItem = By.xpath(".//div[@class='accordion__panel']/p");

    // Локаторы для кнопок «Заказать» (перенесены из OrderPage)
    private final By orderButtonTop = By.xpath(".//button[@class='Button_Button__ra12g' and text()='Заказать']"); // Верхняя кнопка
    private final By orderButtonBottom = By.xpath(".//div[@class='Home_FinishButton__1_cWm']//button[text()='Заказать']"); // Нижняя кнопка

    // Конструктор класса MainPage
    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Метод для использования локаторов
    // Метод принятие cookie
    public void clickOnCookieButton() {
        driver.findElement(cookieButton).click();
    }

    // Метод принятия куки
 //   public void acceptCookies() {
  //      new WebDriverWait(driver, Duration.ofSeconds(8))
   //             .until(ExpectedConditions.elementToBeClickable(cookieButton));
   //     driver.findElement(cookieButton).click();
    //}


    //Метод константа сайта
    public static final String PAGE_URL = "https://qa-scooter.praktikum-services.ru/";

    // Метод ожидания загрузки панели с ответом на вопросы
    public void waitLoadAccordionItem(int index) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(driver.findElements(accordionItem).get(index)));
    }

    // Метод для получения текста из заголовка блока с вопросами
    public String getTextHeaderAccordion(int index) {
        return driver.findElements(accordionHeader).get(index).getText();
    }

    // Метод для нажатия на блок с вопросом
    public void clickHeaderAccordion(int index) {
        driver.findElements(accordionHeader).get(index).click();
    }

    // Метод для получения текста из панели блока вопросов
    public String getTextItemAccordion(int index) {
        return driver.findElements(accordionItem).get(index).getText();
    }

    // Метод для проверки раскрытия блока с вопросом
    public boolean isAccordionItemOpen(int index) {
        return driver.findElements(accordionItem).get(index).isDisplayed();
    }

    // Метод для получения ссылки из логотипа Самоката
    @SuppressWarnings("deprecation")
    public String getLinkScooterLogo() {
        return driver.findElement(clickLogoScooter).getAttribute("href");
    }

    // Метод для получения ссылки из логотипа Яндекса
    @SuppressWarnings("deprecation")
    public String getLinkYandexLogo() {
        return driver.findElement(clickLogoYandex).getAttribute("href");
    }

    // Метод для проверки возможности открыть ссылку Яндекс в новой вкладке
    @SuppressWarnings("deprecation")
    public boolean openYandexLinkInNewWindow() {
        String blanc = "_blank";
        String value = driver.findElement(clickLogoYandex).getAttribute("target");
        return blanc.equals(value);
    }

    // Метод клика по верхней кнопке "Заказать" (перенесён из OrderPage)
    public void clickOrderButtonTop() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(orderButtonTop));
        driver.findElement(orderButtonTop).click();
    }

    // Метод клика по нижней кнопке "Заказать" (перенесён из OrderPage)
    public void clickOrderButtonBottom() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(orderButtonBottom));
        driver.findElement(orderButtonBottom).click();
    }
}



package ru.praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class OrderPage {
    // Создаём драйвер.
    private WebDriver driver;

    // Форма заказа "Для кого самокат"
    private final By orderForm = By.className("Order_Form__17u6u");
    // Локатор для поля Имя
    private final By firstNameSend = By.xpath(".//input[contains(@placeholder,'Имя')]");
    // Локатор для поля Фамилия
    private final By secondNameSend = By.xpath(".//input[contains(@placeholder,'Фамилия')]");
    // Локатор для адреса
    private final By addressOrderSend = By.xpath(".//input[contains(@placeholder,'Адрес')]");
    // Локатор для станции метро
    private final By metroStation = By.xpath(".//input[contains(@placeholder,'Станция метро')]");
    // Выпадающий список станций
    private final By metroList = By.className("select-search__select");
    // Список доступных станций метро
    private final By metroListItems = By.xpath(".//div[starts-with(@class,'Order_Text')]");
    // Локатор для телефона
    private final By phoneInput = By.xpath(".//input[contains(@placeholder,'Телефон')]");

    // Локатор для кнопки «Далее»
    private final By buttonNext = By.className("Button_Middle__1CSJM");

    // Локаторы "Про аренду"
    // Поле ввода даты
    private final By dateInput = By.xpath(".//input[contains(@placeholder,'Когда привезти самокат')]");
    // Выбранная дата
    private final By dateSelected = By.className("react-datepicker__day--selected");
    // Поле «Срок аренды»
    private final By termDropdownRoot = By.className("Dropdown-root");
    // Выпадающий список с доступными сроками аренды
    private final By termDropdownMenu = By.className("Dropdown-option");
    // Локатор чекбокса выбора цвета самоката
    private final By choiceColor = By.className("Checkbox_Label__3wxSf");
    // Локатор поля «Комментарий для курьера»
    private final By commentInput = By.xpath(".//input[contains(@placeholder,'Комментарий')]");

    // Кнопка «Заказать» (финальная)
    private final By orderButtonFinal = By.xpath(".//button[contains(@class, 'Button_Middle__1CSJM') and (text()='Заказать')]");
    // Кнопка «Да» в окне подтверждения заказа
    private final By confirmYesButton = By.xpath(".//button[contains(@class, 'Button_Middle__1CSJM') and (text()='Да')]");
    // текст сообщения хотите оформить заказ
    private final By firstOrderMessage = By.className("Order_ModalHeader__3FDaJ");
    // Текст с сообщением об успешном оформлении заказа
    private final By successOrderMessage = By.xpath(".//div[starts-with(@class,'Order_ModalHeader')]");


    // Конструктор класса OrderPage
    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    // Метод ожидания загрузки формы
    public void waitForLoadOrderPage() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(orderForm));
    }



    // Метод заполнения поля Имя
    public void setNameInput(String name) {
        driver.findElement(firstNameSend).sendKeys(name);
    }

    // Метод заполнения поля Фамилия
    public void setSurnameInput(String surname) {
        driver.findElement(secondNameSend).sendKeys(surname);
    }

    // Метод заполнения поля Адрес
    public void setAddressInput(String address) {
        driver.findElement(addressOrderSend).sendKeys(address);
    }

    // Метод выбора станции метро
    public void setMetroInput(String metro) {
        driver.findElement(metroStation).sendKeys(metro);
        waitForElementLoad(metroList);
        chooseElementFromDropdown(metroListItems, metro);
    }

    // Метод заполнения поля Телефон
    public void setPhoneInput(String phone) {
        driver.findElement(phoneInput).sendKeys(phone);
    }

    // Метод нажатия кнопки «Далее»
    public void clickButtonNext() {
        driver.findElement(buttonNext).click();
    }

    // Метод нажатия на дату в календаре
    private void clickDateSelected() {
        driver.findElement(dateSelected).click();
    }

    // Метод выбора даты
    public void setDateInput(String date) {
        driver.findElement(dateInput).sendKeys(date);
        waitForElementLoad(dateSelected);
        clickDateSelected();
    }

    // Метод нажатия на поле «Срок аренды»
    private void clickTermDropdown() {
        driver.findElement(termDropdownRoot).click();
    }

    // Метод выбора срока аренды
    public void setTermInput(String termToChoose) {
        clickTermDropdown();
        chooseElementFromDropdown(termDropdownMenu, termToChoose);
    }

    // Метод выбора цвета
    public void setColorInput(String colorToChoose) {
        chooseElementFromDropdown(choiceColor, colorToChoose);
    }

    // Метод заполнения поля Комментарий
    public void setCommentInput(String comment) {
        driver.findElement(commentInput).sendKeys(comment);
    }

    // Метод нажатия на кнопку «Заказать» (после заполнения формы)
    private void clickFinalOrderButton() {
        driver.findElement(orderButtonFinal).click();
    }

    // Метод нажатия на подтверждение заказа
    private void clickConfirmOrderButton() {
        new WebDriverWait(driver, Duration.ofSeconds(8))
                .until(ExpectedConditions.elementToBeClickable(confirmYesButton));
        driver.findElement(confirmYesButton).click();
    }

    // Метод завершения заказа
    public void makeOrder() {
        clickFinalOrderButton();
        waitForElementLoad(confirmYesButton);
        clickConfirmOrderButton();
    }

    // Метод получения сообщения об оформлении заказа
    public String getOrderSuccessMessage() {
        return driver.findElement(successOrderMessage).getText();
    }

    // Метод выбора элемента для выпадающего списка
    private void chooseElementFromDropdown(By dropDownElements, String elementToChoose) {
        List<WebElement> elementsFiltered = driver.findElements(dropDownElements);
        for (WebElement element : elementsFiltered) {
            if (element.getText().equals(elementToChoose)) {
                element.click();
                break;
            }
        }
    }

    // Метод ожидания загрузки элемента
    private void waitForElementLoad(By elementToLoad) {
        new WebDriverWait(driver, Duration.ofSeconds(8))
                .until(ExpectedConditions.visibilityOfElementLocated(elementToLoad));
    }
}
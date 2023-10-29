package pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegPage {
    private final WebDriver webDriver;
    private final By nameEmailPasswordInputs = By.xpath(".//form[starts-with(@class, 'Auth_form')]//fieldset//div[@class='input__container']//input");
    private final By regButton = By.xpath(".//form[starts-with(@class, 'Auth_form')]/button");
    private final By errorMessage = By.xpath(".//form[starts-with(@class, 'Auth_form')]//fieldset//div[@class='input__container']//p[starts-with(@class,'input__error')]");
    private final By regTitle = By.xpath(".//main//h2");
    private final By enterButton = By.xpath(".//a[starts-with(@class,'Auth_link')]");
    private final By modalOverlay = By.xpath(".//div[starts-with(@class, 'App_App')]/div[starts-with(@class, 'Modal_modal')]");
    public RegPage(WebDriver driver) {

        webDriver = driver;
    }

    @Step("Ввод данных в поле name")
    public void setName(String name) {
        webDriver.findElements(nameEmailPasswordInputs).get(0).sendKeys(name);
    }
    @Step("Ввод данных в поле email")
    public void setEmail(String email) {
        webDriver.findElements(nameEmailPasswordInputs).get(1).sendKeys(email);
    }
    @Step("Ввод данных в поле password")
    public void setPassword(String password) {
        webDriver.findElements(nameEmailPasswordInputs).get(2).sendKeys(password);
    }

    @Step("Нажатие на кнопку Зарегистрироваться")
    public void clickRegisterButton() {
        waitButtonClick();
        webDriver.findElement(regButton).click();
    }

    @Step("Время ожидания нажатия на кнопку")
    private void waitButtonClick() {
        new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOf(webDriver.findElement(modalOverlay)));
    }

    @Step("Дожидаемся подтверждения регистрации")
    public void waitFormSubmitted(String expectedTitle) {
        new WebDriverWait(webDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.textToBe(regTitle, expectedTitle));
    }

    @Step("Ждем сообщения об ошибке")
    public void waitForErrorMessage() {
        new WebDriverWait(webDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(webDriver.findElement(errorMessage)));
    }

    @Step("Получаем сообщение об ошибке")
    public String getErrorMessage() {

        return webDriver.findElement(errorMessage).getText();
    }

    @Step("Нажимаем на кнопку 'Войти' со страницы регистрации нового пользователя")
    public void clickEnterButton() {
        waitButtonClick();
        webDriver.findElement(enterButton).click();
    }
}

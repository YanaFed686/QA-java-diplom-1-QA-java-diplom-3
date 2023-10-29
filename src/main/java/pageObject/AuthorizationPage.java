package pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AuthorizationPage {
    private final WebDriver webDriver;
    private final By header = By.xpath(".//main//h1");
    private final By authTitleEnter = By.xpath(".//main//h2");
    private final By modalOverlay = By.xpath(".//div[starts-with(@class, 'App_App')]/div/div[starts-with(@class, 'Modal_modal_overlay')]");
    private final By emailAndPasswordFields = By.xpath(".//form[starts-with(@class, 'Auth_form')]//fieldset//div[@class='input__container']//input");
    private final By enterButton = By.xpath(".//form[starts-with(@class, 'Auth_form')]/button");


    public AuthorizationPage(WebDriver driver) {

        this.webDriver = driver;
    }

    public String getAuthTitleEnter() {

        return webDriver.findElement(authTitleEnter).getText();
    }

    @Step("Дожидаемся отображения страницы")
    public void waitAuthorizationForm() {
        new WebDriverWait(this.webDriver, Duration.ofSeconds(30))
                .until(ExpectedConditions.textToBe(authTitleEnter, "Вход"));
    }

    @Step("Ввод данных в поле email")
    public void setEmail(String email) {

        webDriver.findElements(emailAndPasswordFields).get(0).sendKeys(email);
    }

    @Step("Ввод данных в поле password")
    public void setPassword(String password) {
        webDriver.findElements(emailAndPasswordFields).get(1).sendKeys(password);
    }

    @Step("Нажатие на кнопку 'Войти'")
    public void clickEnterButton() {
        waitButtonClick();
        webDriver.findElement(enterButton).click();
    }
    @Step("Время ожидания нажатия на кнопку")
    public void waitButtonClick() {
        new WebDriverWait(this.webDriver, Duration.ofSeconds(30))
                .until(ExpectedConditions.invisibilityOf(webDriver.findElement(modalOverlay)));
    }
    @Step("Дожидаемся завершения аутентификации")
    public void waitForAuthentication() {
        new WebDriverWait(this.webDriver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(header));

    }
}
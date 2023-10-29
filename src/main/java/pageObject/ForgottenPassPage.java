package pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ForgottenPassPage {
    private WebDriver webDriver;
    private final By enterButton = By.xpath(".//a[starts-with(@class,'Auth_link')]");
    private final By modalOverlay = By.xpath(".//div[starts-with(@class, 'App_App')]/div[starts-with(@class, 'Modal_modal')]");


    public ForgottenPassPage(WebDriver driver) {

        this.webDriver = driver;
    }

    @Step("Нажимаем на кнопку 'Войти' со страницы восстановления пароля")
    public void clickOnEnterButtonBelow() {
        waitButtonClick();
        webDriver.findElement(enterButton).click();
    }
    @Step("Время ожидания нажатия на кнопку")
    private void waitButtonClick() {
        new WebDriverWait(this.webDriver, Duration.ofSeconds(30))
                .until(ExpectedConditions.invisibilityOf(webDriver.findElement(modalOverlay)));
    }
}
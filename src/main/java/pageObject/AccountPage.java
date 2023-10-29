package pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AccountPage {
    private WebDriver driver;

    private final By headerLogo = By.xpath(".//div[starts-with(@class,'AppHeader_header__logo')]/a");
    private final By headerAccount = By.xpath(".//p[starts-with(@class,'AppHeader_header__linkText')]");
    private final By accountLink = By.xpath(".//a[contains(@class, 'Account_link_active')]");
    private final By modalOverlay = By.xpath(".//div[starts-with(@class, 'App_App')]/div/div[starts-with(@class, 'Modal_modal_overlay')]");
    private final By exitButton = By.xpath(".//nav[starts-with(@class, 'Account_nav')]/ul/li/button");
    public AccountPage(WebDriver driver) {

        this.driver = driver;
    }

    @Step("Нажатие на лого в хэдере")
    public void clickOnHeaderLogo() {
        waitProfileButton();
        driver.findElement(headerLogo).click();
    }

    @Step("Нажатие на кнопку 'Личный кабинет'")
    public void clickOnConstructor() {
        waitProfileButton();
        driver.findElements(headerAccount).get(0).click();
    }

    @Step("Дожидаемся входа в личный кабинет")
    public void waitAuthorizationInAccount() {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(accountLink));
    }

    @Step("Дожидаемся кнопки Профиль")
    public void waitProfileButton() {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.invisibilityOf(driver.findElement(modalOverlay)));
    }

    @Step("Нажимаем на кнопку Выход")
    public void clickOnExitButton() {
        waitProfileButton();
        driver.findElement(exitButton).click();
    }
}
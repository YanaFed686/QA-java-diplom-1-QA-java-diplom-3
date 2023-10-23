package pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private WebDriver driver;
    private final By createBurgerHeader = By.xpath(".//main//h1");
    private final By headersConstructorOrderAccount = By.xpath(".//p[starts-with(@class,'AppHeader_header__linkText')]");
    private final By burgerIngredientsButtons = By.xpath(".//section[starts-with(@class, 'BurgerIngredients_ingredients')]/div/div");
    private final By menuIngredientsNames = By.xpath(".//div[starts-with(@class, 'BurgerIngredients_ingredients__menuContainer')]/h2");
    private final By createOrderButton = By.xpath(".//div[starts-with(@class,'BurgerConstructor_basket__container')]/button");
    private final By modalOverlay = By.xpath(".//div[starts-with(@class, 'App_App')]/div/div[starts-with(@class, 'Modal_modal_overlay')]");

    public MainPage(WebDriver driver) {

        this.driver = driver;
    }

    @Step("Дожидаемся отображения хэдеров страницы")
    public void waitTheHeaderIsVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(createBurgerHeader));
    }

    @Step("Дожидаемся отображения ингредиентов")
    public int getNamesOfIngredientsExpectedLocation() {
        return Integer.valueOf(driver.findElements(burgerIngredientsButtons).get(0).getLocation().getY()
                + driver.findElements(burgerIngredientsButtons).get(0).getSize().getHeight()
        );
    }

    @Step("Находим расположение кнопки Булки")
    public int getBunsLocation() {
        return Integer.valueOf(driver.findElements(menuIngredientsNames).get(0).getLocation().getY());
    }

    @Step("Нажимаем на кнопку Булки")
    public void clickOnBuns() {
        waitButtonClick();
        driver.findElements(burgerIngredientsButtons).get(0).click();
        scrollingOfTheIngredients(0);
    }
    @Step("Находим расположение кнопки Соусы")
    public int getToppingsLocation() {
        return Integer.valueOf(driver.findElements(menuIngredientsNames).get(1).getLocation().getY());
    }

    @Step("Нажимаем на кнопку Соусы")
    public void clickOnToppings() {
        waitButtonClick();
        driver.findElements(burgerIngredientsButtons).get(1).click();
        scrollingOfTheIngredients(1);
    }

    @Step("Находим расположение кнопки Начинки")
    public int getFillingsLocation() {
        return Integer.valueOf(driver.findElements(menuIngredientsNames).get(2).getLocation().getY());
    }

    @Step("Нажимаем на кнопку Начинки")
    public void clickOnFillings() {
        waitButtonClick();
        driver.findElements(burgerIngredientsButtons).get(2).click();
        scrollingOfTheIngredients(2);
    }

    @Step("Скроллим ингредиенты")
    private void scrollingOfTheIngredients(int navNumber) {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(driver -> {
                            return driver.findElements(menuIngredientsNames).get(navNumber).getLocation().getY() == 243;
                        }
                );
    }
    @Step("Получаем текст кнопки")
    public String getCreateOrderButtonText() {

        return driver.findElement(createOrderButton).getText();
    }
    @Step("Нажимаем на кнопку Оформить заказ")
    public void clickCreateOrderButton() {
        waitButtonClick();
        driver.findElement(createOrderButton).click();
    }
    @Step("Время ожидания нажатия на кнопку")
    public void waitButtonClick() {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.invisibilityOf(driver.findElement(modalOverlay)));
    }
    @Step("Нажимаем на Личный кабинет")
    public void clickOnAccount() {
        waitButtonClick();
        driver.findElements(headersConstructorOrderAccount).get(2).click();
    }
}
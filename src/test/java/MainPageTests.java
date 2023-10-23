import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.example.utils.ListOfUrls;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pageObject.MainPage;

import static org.example.utils.WebDriverSettings.createWebDriver;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Главная страница")
@RunWith(Parameterized.class)
public class MainPageTests {
    private WebDriver driver;
    private String browser;
    private MainPage mainPage;

    @Step("Активация вебдрайверов для Chrome и Yandex")
    @Parameterized.Parameters(name="Browser {0}")
    public static Object[][] initParams() {
        return new Object[][] {
                {"chrome"},
                {"yandex"}
        };
    }
    public MainPageTests(String browser) {

        this.browser = browser;
    }

    @Before
    @Step("Запуск браузеров")
    public void setUp() {
        driver = createWebDriver(browser);
        driver.get(ListOfUrls.MAIN_PAGE);
        mainPage = new MainPage(driver);
    }
    @After
    @Step("Закрытие браузеров")
    public void tearDown() {

        driver.quit();
    }

    @Test
    @Step("Нажатие на 'Булки'")
    @DisplayName("Проверка, что работает переход к разделу Булки")
    public void checkBuns() {
        int expectedLocation = mainPage.getNamesOfIngredientsExpectedLocation();
        mainPage.clickOnToppings();
        mainPage.clickOnBuns();
        MatcherAssert.assertThat(mainPage.getBunsLocation(), equalTo(expectedLocation));
    }

    @Test
    @Step("Нажатие на 'Соусы'")
    @DisplayName("Проверка, что работает переход к разделу Соусы")
    public void checkToppings() {
        int expectedLocation = mainPage.getNamesOfIngredientsExpectedLocation();
        mainPage.clickOnToppings();
        MatcherAssert.assertThat(mainPage.getToppingsLocation(), equalTo(expectedLocation));
    }

    @Test
    @Step("Нажатие на 'Начинки'")
    @DisplayName("Проверка, что работает переход к разделу Начинки")
    public void checkFillings() {
        int expectedLocation = mainPage.getNamesOfIngredientsExpectedLocation();
        mainPage.clickOnFillings();
        MatcherAssert.assertThat(mainPage.getFillingsLocation(), equalTo(expectedLocation));
    }
}
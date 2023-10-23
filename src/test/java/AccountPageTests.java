import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.example.utils.BaseRequests;
import org.example.utils.ListOfUrls;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pageObject.AuthorizationPage;
import pageObject.MainPage;
import pageObject.AccountPage;

import java.util.UUID;

import static org.example.utils.WebDriverSettings.createWebDriver;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Личный кабинет")
@RunWith(Parameterized.class)
public class AccountPageTests {
    private WebDriver driver;
    private String browser;
    private AuthorizationPage authorizationPage;
    private MainPage mainPage;
    private AccountPage accountPage;
    private String name, email, password;
    private BaseRequests baseRequests;


    //Перечисляем нужные нам браузеры
    @Parameterized.Parameters(name="Browser {0}")
    public static Object[][] initParams() {
        return new Object[][] {
                {"chrome"},
                {"yandex"}
        };
    }
    public AccountPageTests(String browser) {

        this.browser = browser;
    }
    @Before
    @Step("Активация вебдрайверов для Chrome и Yandex")
    public void setUp() {
        driver = createWebDriver(browser);
        driver.get(ListOfUrls.MAIN_PAGE);
        authorizationPage = new AuthorizationPage(driver);
        mainPage = new MainPage(driver);
        accountPage = new AccountPage(driver);

        //создание тестовых данных для пользователя
        name = "name";
        email = "email_" + UUID.randomUUID() + "@ya.ru";
        password = "pass_" + UUID.randomUUID();
        baseRequests = new BaseRequests();
        baseRequests.createUser(name, email,password);
    }

    @After
    @Step("Закрытие браузера и удаление данных после выполнения теста")
    public void tearDown() {
        driver.quit();
        baseRequests.deleteUser(email, password);
    }
    @Step("Авторизация пользователя по email и паролю")
    private void userAuthorization() {
        authorizationPage.setEmail(email);
        authorizationPage.setPassword(password);
        authorizationPage.clickEnterButton();
        authorizationPage.waitForAuthentication();
    }

    @Test
    @DisplayName("Проверка, что при клике на «Личный кабинет» происходит переход на страницу профиля")
    public void checkClickToAccount() {
        enterTheAccount();
        MatcherAssert.assertThat(driver.getCurrentUrl(), containsString("/account/profile"));
    }
    @Step("Вход в личный кабинет")
    private void enterTheAccount() {
        driver.get(ListOfUrls.AUTHORIZATION_PAGE);
        authorizationPage.waitAuthorizationForm();
        userAuthorization();
        mainPage.clickOnAccount();
        accountPage.waitAuthorizationInAccount();
    }

    @Test
    @DisplayName("Проверка, что при клике на кнопку «Выход» происходит выход на страницу авторизации")
    public void checkClickOnExit() {
        enterTheAccount();
        accountPage.clickOnExitButton();
        authorizationPage.waitAuthorizationForm();
        MatcherAssert.assertThat(driver.getCurrentUrl(), containsString("/login"));
    }

    @Test
    @DisplayName("Проверка, что при клике на «Конструктор» происходит переход на страницу создания заказа")
    public void checkClickToConstructor() {
        enterTheAccount();
        accountPage.clickOnConstructor();
        mainPage.waitTheHeaderIsVisible();
        MatcherAssert.assertThat(mainPage.getCreateOrderButtonText(), equalTo("Оформить заказ"));
    }
    @Test
    @DisplayName("Проверка, что при клике на «Stellar Burgers» происходит переход на главную страницу")
    public void checkClickOnLogo() {
        enterTheAccount();
        accountPage.clickOnHeaderLogo();
        mainPage.waitTheHeaderIsVisible();
        MatcherAssert.assertThat(mainPage.getCreateOrderButtonText(), equalTo("Оформить заказ"));
    }
}
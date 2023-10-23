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
import pageObject.ForgottenPassPage;
import pageObject.MainPage;
import pageObject.RegPage;

import java.util.UUID;

import static org.example.utils.WebDriverSettings.createWebDriver;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Страница авторизации пользователя")
@RunWith(Parameterized.class)
public class AuthorizationTests {
    private WebDriver webDriver;
    private String browser;
    private AuthorizationPage authorizationPage;
    private MainPage mainPage;
    private RegPage regPage;
    private ForgottenPassPage forgottenPassPage;
    private String name, email, password;
    private BaseRequests baseRequests;
    @Parameterized.Parameters(name="Browser {0}")
    public static Object[][] initParams() {
        return new Object[][] {
                {"chrome"},
                {"yandex"}
        };
    }
    public AuthorizationTests(String browser) {

        this.browser = browser;
    }

    @Before
    @Step("Активация вебдрайверов для Chrome и Yandex")
    public void setUp() {

        webDriver = createWebDriver(browser);
        webDriver.get(ListOfUrls.MAIN_PAGE);

        authorizationPage = new AuthorizationPage(webDriver);
        mainPage = new MainPage(webDriver);
        regPage = new RegPage(webDriver);
        forgottenPassPage = new ForgottenPassPage(webDriver);

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
        webDriver.quit();
        baseRequests.deleteUser(email, password);
    }

    @Step("Авторизация пользователя")
    private void userAuthorization() {
        authorizationPage.setEmail(email);
        authorizationPage.setPassword(password);
        authorizationPage.clickEnterButton();
        authorizationPage.waitForAuthentication();
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    public void enterFromMainPage() {
        mainPage.clickCreateOrderButton();
        authorizationPage.waitAuthorizationForm();
        userAuthorization();
        MatcherAssert.assertThat(mainPage.getCreateOrderButtonText(), equalTo("Оформить заказ"));
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    public void enterFromAccountPage() {
        mainPage.clickOnAccount();
        authorizationPage.waitAuthorizationForm();
        userAuthorization();
        MatcherAssert.assertThat(mainPage.getCreateOrderButtonText(), equalTo("Оформить заказ"));
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void EnterFromRegPage() {
        webDriver.get(ListOfUrls.REG_PAGE);
        regPage.clickEnterButton();
        authorizationPage.waitAuthorizationForm();
        userAuthorization();
        MatcherAssert.assertThat(mainPage.getCreateOrderButtonText(), equalTo("Оформить заказ"));
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void enterFromForgottenPasswordPage() {
        webDriver.get(ListOfUrls.FORGOTTEN_PASSWORD_PAGE);
        forgottenPassPage.clickOnEnterButtonBelow();
        authorizationPage.waitAuthorizationForm();
        userAuthorization();
        MatcherAssert.assertThat(mainPage.getCreateOrderButtonText(), equalTo("Оформить заказ"));
    }
}
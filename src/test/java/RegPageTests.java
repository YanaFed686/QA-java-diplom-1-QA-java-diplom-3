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
import pageObject.RegPage;

import java.util.UUID;

import static org.example.utils.WebDriverSettings.createWebDriver;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Страница регистрации пользователя")
@RunWith(Parameterized.class)
public class RegPageTests {
    private WebDriver webDriver;
    private String browser;
    private RegPage regPage;
    private String email, name, password;

    @Step("Активация вебдрайверов для Chrome и Yandex")
    @Parameterized.Parameters(name="Browser {0}")
    public static Object[][] initParams() {
        return new Object[][] {
                {"chrome"},
                {"yandex"}
        };
    }
    public RegPageTests(String browser) {

        this.browser = browser;
    }
    @Before
    @Step("Активация вебдрайверов для Chrome и Yandex")
    public void setUp() {
        webDriver = createWebDriver(browser);
        webDriver.get(ListOfUrls.REG_PAGE);
        regPage = new RegPage(webDriver);

        //Перечисляем нужные нам браузеры
        name = "name";
        email = "email_" + UUID.randomUUID() + "@ya.ru";
        password = "pass_" + UUID.randomUUID();
    }

    @After
    @Step("Закрытие браузера и удаление данных после выполнения теста")
    public void tearDown() {
        webDriver.quit();
        new BaseRequests().deleteUser(email, password);
    }

    @Test
    @DisplayName("Успешная регистрация")
    public void successfulRegistration() {
        regPage.setEmail(email);
        regPage.setName(name);
        regPage.setPassword(password);
        regPage.clickRegisterButton();
        regPage.waitFormSubmitted("Вход");
    }

    @Test
    @DisplayName("Регистрация с коротким паролем")
    public void passwordIsShorterThanMustBe() {
        regPage.setEmail(email);
        regPage.setName(name);
        regPage.setPassword(password.substring(0, 3));
        regPage.clickRegisterButton();
        regPage.waitForErrorMessage();
        checkErrorMessage();
    }
    @Step("Проверка ошибки для некорректного пароля")
    private void checkErrorMessage() {
        MatcherAssert.assertThat(regPage.getErrorMessage(), equalTo("Некорректный пароль"));
    }
}
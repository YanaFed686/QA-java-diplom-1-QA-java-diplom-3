
package org.example.utils;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverSettings {


        /**
         На момент завершения работы над проектом, актуальный вебдрайвер для Яндекс браузера - 116, а ведрайвер для Chrome - 118.
         По аналогии с настройкой из 4го спринта, получилось написать отдельный класс с настройкой последовательного запуска вебдрайверов
         и открытия браузеров.
         */
        public static WebDriver createWebDriver(String browser) {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver118");
            ChromeOptions options = new ChromeOptions();
            options.setBinary("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
            options.addArguments("--remote-allow-origins=*");
            options.setPageLoadStrategy(PageLoadStrategy.EAGER);
            switch (browser) {
                case "chrome":
                    return new ChromeDriver(options);

                case "yandex":
                    System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver116");
                    ChromeOptions secondOptions = new ChromeOptions();
                    secondOptions.setBinary("/Applications/Yandex.app/Contents/MacOS/Yandex");
                    secondOptions.addArguments("--remote-allow-origins=*");
                    secondOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
                    return new ChromeDriver(secondOptions.setBinary("/Applications/Yandex.app/Contents/MacOS/Yandex"));

                default:
                    throw new RuntimeException("Incorrect browser name");
            }
        }
    }
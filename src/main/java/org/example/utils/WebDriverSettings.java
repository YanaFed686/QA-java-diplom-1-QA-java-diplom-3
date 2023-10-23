package org.example.utils;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

public class WebDriverSettings {


        /**
         На момент завершения работы над проектом, актуальный вебдрайвер для Яндекс браузера - 116, а ведрайвер для Chrome - 118.
         По аналогии с настройкой из 4го спринта, получилось написать отдельный класс с настройкой последовательного запуска вебдрайверов
         и открытия браузеров.
         */
        public static WebDriver createWebDriver(String browser) {
            System.setProperty("webdriver.http.factory", "jdk-http-client");
            ChromeDriverService service = new ChromeDriverService.Builder()
                    .usingDriverExecutable(new File("/Users/yanafedorova/Downloads/QA-java-diplom-3/src/test/resources/chromedriver118"))
                    .build();
            ChromeOptions options = new ChromeOptions()
                    .setBinary("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
            options.setPageLoadStrategy(PageLoadStrategy.EAGER);
            switch (browser) {
                case "chrome":
                    return new ChromeDriver(service, options);

                case "yandex":
                    System.setProperty("webdriver.http.factory", "jdk-http-client");
                    ChromeDriverService secondService = new ChromeDriverService.Builder()
                            .usingDriverExecutable(new File("/Users/yanafedorova/Downloads/QA-java-diplom-3/src/test/resources/chromedriver116"))
                            .build();
                    ChromeOptions secondOptions = new ChromeOptions()
                            .setBinary("/Applications/Yandex.app/Contents/MacOS/Yandex");
                    secondOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
                    return new ChromeDriver(secondService, secondOptions.setBinary("/Applications/Yandex.app/Contents/MacOS/Yandex"));

                default:
                    throw new RuntimeException("Incorrect browser name");
            }
        }
    }
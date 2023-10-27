package org.example.utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverSettings {


        /**
         добавила яндексдрайвер и теперь нет локальных путей)
         */
        public static WebDriver createWebDriver(String browser) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            switch (browser){
                case "chrome":
                    System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver118");
                    return new ChromeDriver(options);
                case "yandex":
                    System.setProperty("webdriver.chrome.driver", "src/test/resources/yandexdriver");
                    return new ChromeDriver(options);
                default:
                    throw new RuntimeException("Incorrect browser name");
            }
        }
    }
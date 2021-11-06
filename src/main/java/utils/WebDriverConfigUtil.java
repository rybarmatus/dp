package utils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverConfigUtil {


    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=C:\\Users\\mmatu\\AppData\\Local\\Google\\Chrome\\User Data");
        options.addArguments("profile-directory=Profile 7");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(options);
//        driver.get("www.google.com");
        WebDriverRunner.setWebDriver(driver);
    }

    @After
    public void tearDown() {
        WebDriver driver = WebDriverRunner.getWebDriver();
        driver.quit();

    }

    public void setConfiguration() {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mmatu\\Documents\\škola\\DP\\chromedriver_win32\\chromedriver.exe");
        System.setProperty("selenide.browser", "C:\\Users\\mmatu\\Documents\\škola\\DP\\chromedriver_win32\\chromedriver.exe");
        setUp();
        Configuration.browser = "chrome";
        Configuration.browserVersion = "95";
        Configuration.headless = true;
    }

}

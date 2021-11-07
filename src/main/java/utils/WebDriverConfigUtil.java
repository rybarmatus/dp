package utils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class WebDriverConfigUtil {


    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=C:\\Users\\mmatu\\AppData\\Local\\Google\\Chrome\\User Data");
        options.addArguments("profile-directory=Profile 7");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--start-maximized");
        options.addArguments("enable-automation"); // https://stackoverflow.com/a/43840128/1689770
        options.addArguments("--no-sandbox"); //https://stackoverflow.com/a/50725918/1689770
        options.addArguments("--disable-infobars"); //https://stackoverflow.com/a/43840128/1689770
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--incognito");
        options.addArguments("--dns-prefetch-disable");
        options.addArguments("--disable-browser-side-navigation");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(java.time.Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(java.time.Duration.ofSeconds(15));
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(15));
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.addArguments("--disable-features=VizDisplayCompositor");
        WebDriverRunner.setWebDriver(driver);
    }


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
    }

}

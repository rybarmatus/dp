package utils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.*;

import java.io.File;


public class WebDriverConfigUtil {


    public void setUpChrome() {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mmatu\\Documents\\škola\\DP\\chromedriver_win32\\chromedriver.exe");
        System.setProperty("selenide.browser", "C:\\Users\\mmatu\\Documents\\škola\\DP\\chromedriver_win32\\chromedriver.exe");
        Configuration.browser = "chrome";
        Configuration.browserVersion = "95";

        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=C:\\Users\\mmatu\\AppData\\Local\\Google\\Chrome\\User Data");
        options.addArguments("profile-directory=Profile 7");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--start-maximized");
        options.addArguments("--dns-prefetch-disable");
        options.addArguments("--disable-browser-side-navigation");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.addArguments("--disable-features=VizDisplayCompositor");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(java.time.Duration.ofSeconds(30));
        WebDriverRunner.setWebDriver(driver);
    }

    public void setUpFirefox() {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\mmatu\\Documents\\škola\\DP\\firefox_driver\\geckodriver.exe");
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");
        ProfilesIni profile = new ProfilesIni();

        FirefoxProfile fp = profile.getProfile("Test");
        fp.addExtension(new File(ConfigEnum.EXT_FIREFOX_DONT_CARE_COOKIES.label));
        fp.addExtension(new File(ConfigEnum.EXT_FIREFOX_UBLOCK.label));
        fp.addExtension(new File(ConfigEnum.EXT_FIREFOX_POPER_PATH.label));

        FirefoxOptions options = new FirefoxOptions()
                .setHeadless(true)
                .setAcceptInsecureCerts(true)
                .setProfile(fp)
                .setPageLoadStrategy(PageLoadStrategy.NORMAL)
                .setPageLoadTimeout(java.time.Duration.ofSeconds(100))
                .addArguments("--disable-gpu, --hide-scrollbars")
                .setLogLevel(FirefoxDriverLogLevel.FATAL);

        WebDriver driver = new FirefoxDriver(options);
        WebDriverRunner.setWebDriver(driver);
    }

    public void tearDown() {
        WebDriver driver = WebDriverRunner.getWebDriver();
        driver.quit();

    }

}

import com.codeborne.selenide.*;
import org.junit.Test;
import org.openqa.selenium.By;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;

public class Base {

    private static final List<String> cookieAcceptWords = Stream.of(
            "accpet", "confirm", "agree", "consent"
    ).collect(Collectors.toList());

    private static final List<String> cookieConfirmElementList = Stream.of("button", "div").collect(Collectors.toList());

    public Base() {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\mmatu\\Documents\\škola\\DP\\chromedriver_win32\\chromedriver.exe");
        Configuration.browser = "chrome";
        Configuration.startMaximized = true;
        Configuration.browserVersion = "95";
    }

    public static void openPage(String url) {
        open(url);
        acceptCookies();
    }

    public static void acceptCookies() {
        cookieAcceptWords.forEach(Base::confirmCookies);
    }

    public static void confirmCookies(String acceptWord) {

        cookieConfirmElementList.forEach(elementName -> {
            ElementsCollection e = $$(byXpath("//" + elementName + "[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'" + acceptWord + "')]"));
            e.forEach(element -> {
                if (element.exists() && element.isDisplayed() && element.isEnabled()) {
                    Selenide.executeJavaScript("arguments[0].click()", element);
                }
            });
        });
    }

    public static void takeScreenshot(String path, String scrnShotName) {
        SelenideElement element = $(By.tagName("body"));
//      int htmlHeight = element.getSize().height;
//      int windowChromeHeight = (int) (long) Selenide.executeJavaScript("return window.outerHeight - window.innerHeight");
//      WebDriver driver = WebDriverRunner.getWebDriver();
//      Dimension size = new Dimension(driver.manage().window().getSize().getWidth(), htmlHeight + windowChromeHeight);
//      Selenide.executeJavaScript("window.scrollTo(0, 0);");
//      driver.manage().window().setSize(size);
//      WebDriver driver = WebDriverRunner.getWebDriver();
//      Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);

        try {
            BufferedImage bi = Screenshots.takeScreenShotAsImage(element);
            File outputfile = new File(path + scrnShotName + ".jpg");
            ImageIO.write(bi, "png", outputfile);
        } catch (IOException e) {
            // handle exception
        }
    }

    @Test
    public void test() {
        openPage("https://www.w3schools.com/js/default.asp");
        takeScreenshot("C:\\Users\\mmatu\\Documents\\webScapper\\screens\\", "sc5rn1");
    }

}

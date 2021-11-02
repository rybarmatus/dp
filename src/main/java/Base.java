import com.codeborne.selenide.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class Base {

    /*


let csvContent = "data:text/csv;charset=utf-8,";
for(var i = 0; i < document.getElementsByClassName("topRankingGrid-titleName").length; i++) {
     csvContent += document.getElementsByClassName("topRankingGrid-titleName")[i].innerText + encodeURIComponent("\r\n");
}
     */
    // TODO co tak majnvat stranky sposobom, ze zadat klucove slovo do googla?

    public WebDriverConfigUtil configUtil = new WebDriverConfigUtil();

    public Base() {
        configUtil.setConfiguration();
    }

    public static void openPage(String url) {
        open(url);
        scrollToBottom();
        sleep(5000);
//        hideCookiesPopUps();
        sleep(3000);
        hideAds();

    }


    public static void hideAds() {
        ElementsCollection ads = $$(By.tagName("iframe"));
        if(ads != null) {
            Selenide.executeJavaScript(
                    "var adsElements = document.getElementsByTagName('iframe');" +
                            "for(var i = 0, max = adsElements.length; i < max; i++)" +
                            "{" +
                            "adsElements[i].hidden = true;" +
                            "}"
            );

    }

}

    public static void hideElement(String cookieWord, String cookieElement) {
        Selenide.executeJavaScript(
                "var xpath = \"//" + cookieElement + "[text()[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')," +
                        cookieWord +
                        ")]]\"" +
                        "; var matchingElement = document.evaluate(xpath, document, null, XPathResult.ANY_TYPE, null);" +
                        " var foundEl = matchingElement.iterateNext();" +
                         "console.log(foundEl.tagName);" +
                        " while (foundEl) {" +
                        "if(foundEl.tagName.toUpperCase != 'BODY') {" +
                        "foundEl.style.display = 'none';}" +
                        "foundEl = matchingElement.iterateNext(); " +
                        "} "
        );
    }

    public static void clickSafely(SelenideElement element) {
        if (element.exists() && element.isDisplayed() && element.isEnabled()) {
            Selenide.executeJavaScript("arguments[0].click()", element);
        }
    }

    public static void takeScreenshot(String path, String scrnShotName) {
        SelenideElement element = $(By.tagName("body"));
        Long height = Selenide.executeJavaScript("return document.documentElement.scrollHeight;");
        /*
         * natiahne sa velkost okna podla vysky stranky
         * aby bol screenshot celej stranky
         */
        WebDriver driver = WebDriverRunner.getWebDriver();
        driver.manage().window().setSize(new Dimension(1000, height.intValue()));
        try {
            BufferedImage bi = Screenshots.takeScreenShotAsImage(element);
            File outputfile = new File(path + scrnShotName + ".jpg");
            ImageIO.write(bi, "png", outputfile);
        } catch (IOException e) {
            // handle exception
        }
    }

    // obcas sa content stranky dynamicky dotahuje scrollovanim
    public static void scrollToBottom() {
        Selenide.executeJavaScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    @Test
    public void test() {
        openPage("https://www.nbcnews.com/");
        sleep(5000);
        takeScreenshot("C:\\Users\\mmatu\\Documents\\webScapper\\screens\\", "sc5rn1");
        this.configUtil.tearDown();
    }

}

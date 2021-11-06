package utils;

import com.codeborne.selenide.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import utils.WebDriverConfigUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.*;

public class ScrappingUtils {

    /*


let csvContent = "data:text/csv;charset=utf-8,";
for(var i = 0; i < document.getElementsByClassName("topRankingGrid-titleName").length; i++) {
     csvContent += document.getElementsByClassName("topRankingGrid-titleName")[i].innerText + encodeURIComponent("\r\n");
}
     */
    // TODO co tak majnvat stranky sposobom, ze zadat klucove slovo do googla?
    // TODO heureka eshop scrapping
    // TODO mozno prehodit na spring?

    private static int counter = 0;

    public WebDriverConfigUtil configUtil = new WebDriverConfigUtil();

    public ScrappingUtils() {
        configUtil.setConfiguration();
    }

    public static void openPage(String url) {
        open(url);
        scrollToBottom();
        sleep(250);
        System.out.println(counter);
        try {
            $(By.tagName("body")).should(Condition.visible, java.time.Duration.ofSeconds(1L));

        }
        catch (com.codeborne.selenide.ex.ElementNotFound e) {
            e.printStackTrace();
        }

        hideAds();
        sleep(250);
    }

    public static void hideAds() {
        ElementsCollection ads = null;
        try {
            ads = $$(By.tagName("iframe"));
        }
        catch (com.codeborne.selenide.ex.ElementNotFound e) {
            e.printStackTrace();
        }
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
        SelenideElement element = null;
        try {
            element = $(By.tagName("body"));
        }
        catch (com.codeborne.selenide.ex.ElementNotFound e) {
            e.printStackTrace();
        }

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


    public void scrapPage(String pageUrl, String path) {
        openPage("http://www."+pageUrl);
        takeScreenshot(path, String.valueOf(counter));
        counter++;
    }

}

package utils;


import com.codeborne.selenide.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

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
    // TODO ulozit stranky, ktore sa nenatiahli spolu s ich kategoriou -> mozno ich skusit neskor natiahnut
    // TODO heureka eshop scrapping / alebo mozno bude lepsie amazon

    private static int counter = 0;
    public WebDriverConfigUtil configUtil = new WebDriverConfigUtil();
    private final ScreenShotUtil ssu = new ScreenShotUtil();

    public ScrappingUtils() {
        configUtil.setConfiguration();
    }

    public boolean openPage(String pageUrl) {
        try {
            if (CsvWorkerUtil.checkIfUrlAlreadyScrapped(pageUrl)) {
                System.out.println("Stranku " + pageUrl + " som uz scrapoval! Idem na dalsiu");
                return false;
            }
        } catch (IOException e) {
            return false;
        }
        pageUrl = "http://www." + pageUrl;
        try {
//            configUtil.tearDown();
//            configUtil.setConfiguration();
            open(pageUrl);
        } catch (Exception e) {
            System.out.println("Nenacitala sa stranka -> " + pageUrl);
            return false;
        }
        scrollToBottom();
        sleep(250);
        try {
            $(By.tagName("body")).shouldBe(Condition.visible);

        } catch (com.codeborne.selenide.ex.ElementNotFound e) {
            e.printStackTrace();
            return false;
        }

        return hideAds();
    }

    public static boolean hideAds() {
        ElementsCollection ads = null;
        try {
            ads = $$(By.tagName("iframe"));
        } catch (com.codeborne.selenide.ex.ElementNotFound e) {
            e.printStackTrace();
            return false;
        }
        if (ads != null) {
            Selenide.executeJavaScript(
                    "var adsElements = document.getElementsByTagName('iframe');" +
                            "for(var i = 0, max = adsElements.length; i < max; i++)" +
                            "{" +
                            "adsElements[i].hidden = true;" +
                            "}"
            );

        }
        return true;
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

    public void takeScreenshot(String path, String scrnShotName) {
        sleep(250);
        SelenideElement element = null;
        try {
            element = $(By.tagName("body"));
        } catch (com.codeborne.selenide.ex.ElementNotFound e) {
            e.printStackTrace();
        }

        if (element == null) return;
        long height = element.getSize().getHeight();
        /*
         * natiahne sa velkost okna podla vysky stranky
         * aby bol screenshot celej stranky
         */
        WebDriver driver = WebDriverRunner.getWebDriver();
        driver.manage().window().setSize(new Dimension(1000, (int) height));
        try {

            if (element.isDisplayed()) {
                int h = element.getSize().getHeight();
                int w = element.getSize().getWidth();
                if (h > 0 && w > 0) {
                    h = Math.min(h, 14000);
                    this.ssu.shootWebElement(element, WebDriverRunner.getWebDriver(), path, String.valueOf(counter), h, w);
                }
            }

        } catch (Exception e) {
            BufferedImage bi = Screenshots.takeScreenShotAsImage(element);
            File outputfile = new File(path + scrnShotName + ".png");
            if (bi == null) return;
            try {
                ImageIO.write(bi, "png", outputfile);
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    // obcas sa content stranky dynamicky dotahuje scrollovanim
    public static void scrollToBottom() {
        Selenide.executeJavaScript("window.scrollTo(0, document.body.scrollHeight);");
    }


    public void scrapPage(String pageUrl, String path) {

        if (openPage(pageUrl)) {
            try {
                CsvWorkerUtil.storeScrapedUrl(pageUrl);
            } catch (IOException e) {
                return;
            }
            takeScreenshot(path, String.valueOf(counter));
        }
        counter++;
    }

    @Test
    public void tst() {
        scrapPage("designlovefest.com", ConfigEnum.SCRAPPED_PAGES_PATH.label);
    }

}

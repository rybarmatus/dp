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

     let csvContent = "data:text/csv;charset=utf-8,";
document.querySelectorAll('div.cell.cell-md a').forEach(v => {
    csvContent += v.innertText + encodeURIComponent("\r\n");
});
csvContent;

}
     */
    // TODO heureka eshop scrapping / alebo mozno bude lepsie amazon

    private static int counter = 0;
    public WebDriverConfigUtil configUtil = new WebDriverConfigUtil();
    private final ScreenShotUtil ssu = new ScreenShotUtil();


    public ScrappingUtils() {
        configUtil.setUpFirefox();
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
            open(pageUrl);
        } catch (Exception e) {
            System.out.println("Nenacitala sa stranka -> " + pageUrl);
            return false;
        }
        sleep(500);
        scrollToBottom();

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
        sleep(5);
        SelenideElement element = null;
        try {
            element = $(By.tagName("body"));
        } catch (com.codeborne.selenide.ex.ElementNotFound e) {
            e.printStackTrace();
        }

        if (element == null) return;
        Long h = getHeight();
        int height = 0;
        if (h != null) height = h.intValue();
        else {
            height = element.getSize().getHeight();
        }
        long width = element.getSize().getWidth();
        /*
         * natiahne sa velkost okna podla vysky stranky
         * aby bol screenshot celej stranky
         */
        WebDriver driver = WebDriverRunner.getWebDriver();
        driver.manage().window().setSize(new Dimension((int) width, height));
        try {

            if (element.isDisplayed()) {
                if (height > 0 && width > 0) {
//                    width = Math.min(h, 14000);
                    this.ssu.shootWebElement(element, WebDriverRunner.getWebDriver(), path, String.valueOf(counter), (int) height, (int) width);
                }
            }

        } catch (Exception e) {
            BufferedImage bi;
            try {
                bi = Screenshots.takeScreenShotAsImage(element);
            } catch (Exception ee) {
                return;
            }
            File outputfile = new File(path + scrnShotName + ".png");
            if (bi == null) return;
            try {
                ImageIO.write(bi, "png", outputfile);
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    public Long getHeight() {
        if($(By.tagName("body")).exists()) {
            try {
                return Selenide.executeJavaScript(" if(document.body != undefined) { return document.body.scrollHeight }");
            }
            catch (Exception e) {
                return null;
            }
        }

        return null;
    }

    // obcas sa content stranky dynamicky dotahuje scrollovanim
    public static void scrollToBottom() {
        if ($(By.tagName("body")).exists() && $(By.tagName("body")).isDisplayed()) {
            try {
                Selenide.executeJavaScript(" if (document.body != undefined && document.body != null) { window.scrollTo(0, document.body.scrollHeight); } ");
            }
            catch (Exception e) {
                System.out.println("padol javascript");
            }
        }
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

    @Test
    public void tstt() {
        open("http://www.mtb-news.de/");
        scrollToBottom();
    }

}

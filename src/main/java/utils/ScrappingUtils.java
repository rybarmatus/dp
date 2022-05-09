package utils;


import com.codeborne.selenide.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

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

    public WebDriverConfigUtil configUtil = new WebDriverConfigUtil();
    private final ScreenShotUtil ssu = new ScreenShotUtil();


    public ScrappingUtils() {
        configUtil.setUpFirefox();
    }

    public boolean openPage(String pageUrl, String category) {
        pageUrl = formatUrl(pageUrl);
        try {
            open(pageUrl);
            WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), Duration.ofSeconds(5));
            wait.until(webDriver -> "complete".equals(Selenide.executeJavaScript("return document.readyState")));
        } catch (Exception e) {
            System.out.println("" +
                    "Nenacitala sa stranka -> " + pageUrl + " " + category);
            CsvWorkerUtil.storeScrapedUrlFailed(pageUrl, category);
            return false;
        }
        scrollToBottom();
        sleep(1000);
        scrollToTop();
        try {
            $(By.tagName("body")).shouldBe(Condition.visible);

        } catch (com.codeborne.selenide.ex.ElementNotFound e) {
            e.printStackTrace();
            System.out.println("Nenasiel sa body element stranky " + pageUrl);
            CsvWorkerUtil.storeScrapedUrlFailed(pageUrl, category);
            return false;
        }

        return hideAds();
    }

    private String formatUrl(String pageUrl) {
        if (pageUrl != null) {
            if (StringUtils.isNotBlank(pageUrl)) {
                if (pageUrl.contains("http:"))
                    return pageUrl;
                else if (pageUrl.contains("https:"))
                    return pageUrl;
            }
        }
        pageUrl = "https://www." + pageUrl;
        return pageUrl;
    }

    private String formatUrlUnsecured(String pageUrl) {
        if (pageUrl != null) {
            if (StringUtils.isNotBlank(pageUrl)) {
                if (pageUrl.contains("http:"))
                    return pageUrl;
                else if (pageUrl.contains("https:"))
                    return pageUrl;
            }
        }
        pageUrl = "http://www." + pageUrl;
        return pageUrl;
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

    public void takeScreenshot(String path, String pageUrl, String category) {
        sleep(1000);
        SelenideElement element;
        try {
            element = $(By.tagName("html"));
        } catch (Exception exc) {
            CsvWorkerUtil.storeScrapedUrlFailed(pageUrl, category);
            System.out.println("Nenaslo sa html stranky " + pageUrl);
            exc.printStackTrace();
            return;
        }
        Long h = getHeight();
        int height = 0;
        int width = 1920;
        if (h != null) height = h.intValue();
        if (height == 0) height = element.getSize().getHeight();

        WebDriver driver = WebDriverRunner.getWebDriver();
        driver.manage().window().setSize(new Dimension(width, height));
        try {

            if (element.isDisplayed()) {
                if (height > 0) {
//                    this.ssu.shootWebElement(driver, path, pageUrl);
                    this.ssu.shootPageByParts(height, driver, path, pageUrl);
                } else {
                    System.out.println("Vyska alebo sirka stranky < 0 " + pageUrl);
                    CsvWorkerUtil.storeScrapedUrlFailed(pageUrl, category);
                    return;
                }
            }

        } catch (Exception e) {
            BufferedImage bi;
                try {
                    bi = Screenshots.takeScreenShotAsImage(element);
                } catch (WebDriverException we) {
                    System.out.println("Chyba pri screenshote stranky " + pageUrl);
                    CsvWorkerUtil.storeScrapedUrlFailed(pageUrl, category);
                    return;
                }
            pageUrl = pageUrl.replaceAll("[\\\\/:*?\"<>|]", "");
            pageUrl = pageUrl.replace("?", "");
            pageUrl = pageUrl.replace("\\", "");
            if(pageUrl == null || pageUrl.length() < 1) {
                int rand = ThreadLocalRandom.current().nextInt();
                rand = rand < 0 ? rand * -1 : rand;
                pageUrl = String.valueOf(rand);
            }
            File outputfile = new File(path + pageUrl + ".png");
            if (bi == null) return;
            try {
                ImageIO.write(bi, "png", outputfile);
            } catch (Exception ee) {
                System.out.println("Chyba pri screenshote stranky " + pageUrl);
                CsvWorkerUtil.storeScrapedUrlFailed(pageUrl, category);
                ee.printStackTrace();
            }
        }
    }

    public Long getHeight() {
        if ($(By.tagName("body")).exists()) {
            try {
                return Selenide.executeJavaScript(" if(document.body != undefined) { return document.body.scrollHeight }");
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }

    public Long getWidth() {
        if ($(By.tagName("body")).exists()) {
            try {
                return Selenide.executeJavaScript(" if(document.body != undefined) { return document.body.scrollWidth }");
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }

    // obcas sa content stranky dynamicky dotahuje scrollovanim
    public static void scrollToTop() {
        if ($(By.tagName("body")).exists() && $(By.tagName("body")).isDisplayed()) {
            try {
                Selenide.executeJavaScript(" window.scrollTo(0,0); ");
            } catch (Exception e) {
                System.out.println("padol javascript");
            }
        }
    }

    public static void scrollToBottom() {
        if ($(By.tagName("body")).exists() && $(By.tagName("body")).isDisplayed()) {
            try {
                Selenide.executeJavaScript(" if (document.body != undefined && document.body != null) { window.scrollTo(0, document.body.scrollWidth); } ");
                Selenide.executeJavaScript(" if (document.body != undefined && document.body != null) { window.scrollTo(0, document.body.scrollHeight); } ");
            } catch (Exception e) {
                System.out.println("padol javascript");
            }
        }
    }


    public void scrapPage(String pageUrl, String path, String category) {

        if (openPage(pageUrl, category)) {
            try {
                CsvWorkerUtil.storeScrapedUrl(pageUrl, category);
            } catch (IOException e) {
                return;
            }
            takeScreenshot(path, pageUrl, category);
        }
    }

    public void downloadHtml(String urlString, String path, String category) throws MalformedURLException {
        if(urlString == null) return;
        openPage(urlString, category);
        final InputStream source = new ByteArrayInputStream(WebDriverRunner.getWebDriver().getPageSource().getBytes());

        if(urlString.contains("https:/") || urlString.contains("http:/")) {
            urlString = urlString.replace("https://", "");
            urlString = urlString.replace("http://", "");
            urlString = urlString.replace("www.", "");
            urlString = urlString.split("/")[0];
        }
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(source));
                BufferedWriter writer = new BufferedWriter(new FileWriter(path + urlString + ".html"));
        ) {
            String htmlLine;
            while ((htmlLine = reader.readLine()) != null) {
                writer.write(htmlLine);
            }
            reader.close();
            writer.close();
            System.out.println(Thread.currentThread().getName() + " stiahol html stranky " + urlString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tst() {
        scrapPage("designlovefest.com", ConfigEnum.SCRAPPED_PAGES_PATH.label, null);
    }

    @Test
    public void tstt() {
        open("http://www.mtb-news.de/");
        scrollToBottom();
    }

}

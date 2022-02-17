package utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class ScreenShotUtil {


    public void shootWebElement(WebDriver driver, String path, String fileName) throws IOException {
        File screen;
        try {
            screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        } catch (TimeoutException e) {
            return;
        }
        BufferedImage img = ImageIO.read(screen);

        File f = new File(path + fileName + ".png");

        System.out.println(path + fileName);

        ImageIO.write(img, "png", f);

    }

    public void shootPageByParts(int totalHeight, WebDriver driver, String path, String fileName) throws IOException {

        sleep(1000);
//        driver.manage().window().maximize();
//        int sliceHeight = driver.manage().window().getSize().height;
        int sliceHeight = takeSliceScreen(driver) == null ? 500 : Objects.requireNonNull(takeSliceScreen(driver)).getHeight();
        if (totalHeight < 1) return;
        if (totalHeight < sliceHeight) {
            sliceHeight = totalHeight;
        }
        int numOfSlices = totalHeight / sliceHeight;
        if (numOfSlices < 1) return;

        int currentWindowWidth = driver.manage().window().getSize().getWidth();
        driver.manage().window().setSize(new Dimension(currentWindowWidth, sliceHeight));

        int remainHeight = totalHeight - (sliceHeight * numOfSlices);
        int remainSlice = remainHeight < 1 ? 0 : 1;

        BufferedImage[] images = new BufferedImage[numOfSlices + remainSlice];
        boolean hided = false;

        for (int i = 0; i < numOfSlices; i++) {
            BufferedImage slice = takeSliceScreen(driver);
            if (slice == null) return;
            images[i] = slice;
            scrollPageVertically(slice.getHeight());
            if(!hided) {
                hideStaticElements(driver);
                hided = true;
            }
            String postfix = i == 0? "" : ""+i;
            ImageIO.write(slice, "png", new File(path + fileName + postfix + ".png"));
        }

        if (remainSlice != 0) {
            driver.manage().window().setSize(new Dimension(currentWindowWidth, remainHeight));
            scrollPageVertically(totalHeight);
            images[numOfSlices] = takeSliceScreen(driver);
        }

//        int heightCurr = 0;
//
//        BufferedImage concatImage = new BufferedImage(currentWindowWidth, totalHeight, BufferedImage.TYPE_INT_RGB);
//        Graphics2D g2d = concatImage.createGraphics();
//        for (int j = 0; j < images.length; j++) {
//            g2d.drawImage(images[j], 0, heightCurr, null);
//            heightCurr += images[j].getHeight();
//        }
//        g2d.dispose();
//        ImageIO.write(concatImage, "png", new File(path + fileName + ".png"));
        System.out.println("Ukladam " + path + fileName);
    }

    private void hideStaticElements(WebDriver driver) {
        WebElement element = $(By.tagName("html")).shouldBe(Condition.visible);
        List<WebElement> v = element.findElements(By.xpath(".//*"));

        v.forEach(webElement -> {
            if ("fixed".equals(webElement.getCssValue(
                    "position"
            )) || "sticky".equals(webElement.getCssValue(
                    "position"
            ))) ((JavascriptExecutor)driver).executeScript("arguments[0].style.display='none'", webElement);
        });
    }

    private static void scrollPageVertically
            (int height) {
        Selenide.executeJavaScript("window.scrollBy(0," + height + ")");
    }

    private static void scrollPageHorizontally
            (int width) {
        Selenide.executeJavaScript("window.scrollBy(" + width + ",0)");
    }

    private static BufferedImage takeSliceScreen(WebDriver driver) throws IOException {
        File screen;
        try {
            screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        } catch (TimeoutException e) {
            return null;
        }
        return ImageIO.read(screen);
    }

}
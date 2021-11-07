package utils;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenShotUtil {


    public void shootWebElement(WebElement element, WebDriver driver, String path, String fileName, int h, int w) throws IOException {
        File screen;
        try {
            screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        }
        catch (TimeoutException e) {
            return;
        }

//        Point p = element.getLocation();
//
//        int width = element.getSize().getWidth();
//        int height = element.getSize().getHeight();

        BufferedImage img = ImageIO.read(screen);

//        BufferedImage dest = img.getSubimage(p.getX(), p.getY(), width,
//                height);


        File f = new File(path + fileName+".png");

        System.out.println(path);

        ImageIO.write(img, "png", f);

    }
}

//    WebDriver driver = new FirefoxDriver(); // define this somewhere (or chrome etc)
//
//    public <T> T screenshotOf(By by, long timeout, OutputType<T> type) {
//        return ((TakesScreenshot) waitForElement(by, timeout))
//                .getScreenshotAs(type);
//    }
//
//    public WebElement waitForElement(By by, long timeout) {
//        return new WebDriverWait(driver, timeout)
//                .until(driver -> driver.findElement(by));
//    }
//    And then just screenshot whatever u want like this :
//
//        long timeout = 5;   // in seconds
//        /* Screenshot (to file) based on first occurence of tag */
//        File sc = screenshotOf(By.tagName("body"), timeout, OutputType.FILE);
///* Screenshot (in memory) based on CSS selector (e.g. first image in body
//who's "src" attribute starts with "https")  */
//        byte[] sc = screenshotOf(By.cssSelector("body > img[href^='https']"), timeout, OutputType.B
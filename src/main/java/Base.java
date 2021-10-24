import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;
public class Base {

   private static final List<String> cookieList = Stream.of(
           "accpet", "confirm", "agree", "consent"
   ).collect(Collectors.toList());

   public static void openPage(String url) {
      open(url);
      acceptCookies();
   }

   public static void acceptCookies() {
      cookieList.forEach(Base::confirmCookies);
   }

   public static void confirmCookies(String acceptWord) {


      ElementsCollection e = $$(byXpath("//button[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'" + acceptWord + "')]"));
      e.forEach(element -> {
        if(element.exists() && element.isDisplayed() && element.isEnabled()) {
           Selenide.executeJavaScript("arguments[0].click()", element);
        }
      });
   }

   @Test
   public void test() {
      openPage("https://www.baeldung.com/");
   }

}

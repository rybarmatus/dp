import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScrapperRunner {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(10);
        /*
        ---- skrapovanie obrazkov stranok ----
        */

//        executor.execute(new ScreenScrapperRunnable());
//        Thread.sleep(4000);
//        executor.execute(new ScreenScrapperRunnable());
//        Thread.sleep(4000);
//        executor.execute(new ScreenScrapperRunnable());
//        Thread.sleep(4000);
//        executor.execute(new ScreenScrapperRunnable());

/*
 ---- skrapovanie html suborov ----
 */
        executor.execute(new HtmlScrapperRunnable());
        Thread.sleep(4000);
        executor.execute(new HtmlScrapperRunnable());
        Thread.sleep(4000);
        executor.execute(new HtmlScrapperRunnable());
        Thread.sleep(4000);
        executor.execute(new HtmlScrapperRunnable());

    }
}

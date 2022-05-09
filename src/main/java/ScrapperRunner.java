import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScrapperRunner {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(10);

//        executor.execute(new ScreenScrapperRunnable());
//        executor.execute(new ScreenScrapperRunnable());
//        executor.execute(new ScreenScrapperRunnable());
//        executor.execute(new ScreenScrapperRunnable());
//        executor.execute(new ScreenScrapperRunnable());
//        executor.execute(new ScreenScrapperRunnable());
//        executor.execute(new ScreenScrapperRunnable());
//        executor.execute(new ScreenScrapperRunnable());
//        executor.execute(new ScreenScrapperRunnable());
//        executor.execute(new ScreenScrapperRunnable());

/*
 ---- skrapovanie html suborov ----
 */
        executor.execute(new HtmlScrapperRunnable());
        executor.execute(new HtmlScrapperRunnable());
        executor.execute(new HtmlScrapperRunnable());
        executor.execute(new HtmlScrapperRunnable());
        executor.execute(new HtmlScrapperRunnable());

    }
}

import java.io.IOException;

public class HtmlScrapperRunnable implements  Runnable {

    @Override
    public void run() {
        System.out.println("spustil sa thread " + Thread.currentThread().getName());
        ScrapperHTML scp = new ScrapperHTML();
        try {
            scp.scrapPagesWithDeletion();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
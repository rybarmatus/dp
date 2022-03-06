import java.io.IOException;

public class ScreenScrapperRunnable implements  Runnable {

    @Override
    public void run() {
        System.out.println("spustil sa thread " + Thread.currentThread().getName());
        Scrapper scp = new Scrapper();
        try {
            scp.scrapPagesWithDeletion();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
import org.junit.Test;
import utils.ConfigEnum;
import utils.CsvWorkerUtil;
import utils.ScrappingUtils;

import java.io.IOException;
import java.util.Set;

public class Scrapper {

    private final ScrappingUtils sU = new ScrappingUtils();

    @Test
    public void scrapPagesWithDeletion() throws IOException {
        String path;
        String page;
        Set<String> csvFiles = CsvWorkerUtil.listFiles("C:\\Users\\mmatu\\Documents\\škola\\DP\\web_categories - Copy");
        for (String csvFile : csvFiles) {
            try {
                path = CsvWorkerUtil.createDirectory(csvFile);
                do {
                    page = CsvWorkerUtil.readPageFromCSV(csvFile);
                    if(path == null || page == null) break;
                    this.sU.scrapPage(page, path);
                }
                while (page != null || path != null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void scrapPages() throws IOException {
        String path;
        Set<String> csvFiles = CsvWorkerUtil.listFiles(ConfigEnum.CSV_DIR_PATH.label);
        for (String csvFile : csvFiles) {
            try {
                Set<String> pages = CsvWorkerUtil.getPagesFromCSV(csvFile);
                for (String pageUrl : pages) {
                    path = CsvWorkerUtil.createDirectory(csvFile);
                    if (path != null) {
                        this.sU.scrapPage(pageUrl, path);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        Scrapper scp = new Scrapper();
        try {
            scp.scrapPagesWithDeletion();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

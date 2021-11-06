import utils.ConfigEnum;
import utils.CsvWorkerUtil;
import utils.ScrappingUtils;

import java.io.IOException;
import java.util.Set;

public class Scrapper {

    private ScrappingUtils sU = new ScrappingUtils();

    public void scrapPages() throws IOException {
        String path;
        int count = 0;
        Set<String> csvFiles = CsvWorkerUtil.listFiles();
        for(String csvFile: csvFiles) {
            try {
                Set<String> pages = CsvWorkerUtil.getPagesFromCSV(csvFile);
                for(String pageUrl: pages) {
                    path = CsvWorkerUtil.createDirectory(csvFile);
                    if(path != null) {
                        this.sU.scrapPage(pageUrl, path);
                        count++;
                        if(count == 10) continue;
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
            scp.scrapPages();
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

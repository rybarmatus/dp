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
        Set<String> csvFiles = CsvWorkerUtil.listFiles(ConfigEnum.CSV_CATEGORIES_PATH_2.label);
        for (String csvFile : csvFiles) {
            try {
                path = CsvWorkerUtil.createDirectory(csvFile);
                String category = CsvWorkerUtil.extractCategory(path);
                if(path == null || category == null) {
                    System.out.println("Cesta k CSV alebo kategoria nemozu byt null!! cesta: " + path + " kategoria: " + category);
                }
                while(true) {
                    page = CsvWorkerUtil.readPageFromCSV(csvFile);
                    if (page == null) {
                        System.out.println("||||||||||||||||||| \n Odstranujem "+ csvFile + "lebo je prazdny");
                        CsvWorkerUtil.removeFile(csvFile);
                        break;
                    }
                    this.sU.scrapPage(page, path, category);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    @Test
//    public void scrapPagesWithDeletion(boolean parseUrl) throws IOException {
//        String path;
//        String page;
//        Set<String> csvFiles = CsvWorkerUtil.listFiles(ConfigEnum.CSV_CATEGORIES_PATH_2.label);
//        for (String csvFile : csvFiles) {
//            try {
//                path = CsvWorkerUtil.createDirectory(csvFile);
//                String category = CsvWorkerUtil.extractCategory(path);
//                if(path == null || category == null) {
//                    System.out.println("Cesta k CSV alebo kategoria nemozu byt null!! cesta: " + path + " kategoria: " + category);
//                }
//                while(true) {
//                    page = CsvWorkerUtil.readPageFromCSV(csvFile);
//                    if (page == null) {
//                        System.out.println("||||||||||||||||||| \n Odstranujem "+ csvFile + "lebo je prazdny");
//                        CsvWorkerUtil.removeFile(csvFile);
//                        break;
//                    }
//                    this.sU.scrapPage(page, path, category);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public void scrapPages() throws IOException {
        String path;
        Set<String> csvFiles = CsvWorkerUtil.listFiles(ConfigEnum.CSV_CATEGORIES_PATH.label);
        for (String csvFile : csvFiles) {
            try {
                Set<String> pages = CsvWorkerUtil.getPagesFromCSV(csvFile);
                for (String pageUrl : pages) {
                    path = CsvWorkerUtil.createDirectory(csvFile);
                    if (path != null) {
                        this.sU.scrapPage(pageUrl, path, null);
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

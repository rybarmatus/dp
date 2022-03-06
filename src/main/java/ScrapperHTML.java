import utils.ConfigEnum;
import utils.CsvWorkerUtil;
import utils.ScrappingUtils;

import java.io.IOException;
import java.util.Set;

public class ScrapperHTML {

    private final ScrappingUtils sU = new ScrappingUtils();

    public void scrapPagesWithDeletion() throws IOException {
        String path;
        String page;
        Set<String> csvFiles = CsvWorkerUtil.listFiles(ConfigEnum.CSV_CATEGORIES_PATH_2.label);
        for (String csvFile : csvFiles) {
            try {
                path = CsvWorkerUtil.createDirectory(csvFile, ConfigEnum.BASE_PATH.label, ConfigEnum.DEST_HTML_PATH.label);
                String category = CsvWorkerUtil.extractCategory(path);
                if (path == null || category == null) {
                    System.out.println("Cesta k CSV alebo kategoria nemozu byt null!! cesta: " + path + " kategoria: " + category);
                }
                while (true) {
                    page = CsvWorkerUtil.readPageFromCSV(csvFile);
                    if (page == null) {
                        System.out.println("||||||||||||||||||| \n Odstranujem " + csvFile + "lebo je prazdny");
                        CsvWorkerUtil.removeFile(csvFile);
                        break;
                    }
                    this.sU.downloadHtml(page, path, category);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

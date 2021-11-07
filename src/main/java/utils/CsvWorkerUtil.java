package utils;


import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvWorkerUtil {

    // prebehne priecinok a vrati nazvy suborov v priecinku
    public static Set<String> listFiles() throws IOException {
        String dir = ConfigEnum.CSV_DIR_PATH.label;
        try (Stream<Path> stream = Files.walk(Paths.get(dir), 1)) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }

    public static Set<String> getPagesFromCSV(String pathToCsv) throws IOException {
        Set<String> pages = new HashSet<>();
        BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
        String row;
        while ((row = csvReader.readLine()) != null) {
            pages.add(row);
        }
        csvReader.close();
        return pages;
    }

    public static String createDirectory(String dirName) {
        if (StringUtils.isBlank(dirName)) return null;

        if (!dirName.contains("~")) return null;
        if (dirName.contains(".csv")) dirName = dirName.replace(".csv", "");
        dirName = dirName.replace("~", "\\");
        dirName = dirName + "\\";
        File file = new File(dirName);
        file.mkdirs();
        return dirName;
    }

    public static void storeScrapedUrl(String pageUrl) throws IOException {
        FileWriter csv = new FileWriter(ConfigEnum.SCRAPPED_PAGES_PATH.label, true);
        csv.append(pageUrl);
        csv.append(",");
        csv.append("\n");
        csv.close();
    }

    public static boolean checkIfUrlAlreadyScrapped(String pageUrl) throws IOException {
        BufferedReader csvReader = null;
        try {
            csvReader = new BufferedReader(new FileReader(ConfigEnum.SCRAPPED_PAGES_PATH.label));
        } catch (FileNotFoundException e) {

            FileWriter csv = new FileWriter(ConfigEnum.SCRAPPED_PAGES_PATH.label, true);
            csv.close();
            return false;
        }
        String row;
        while ((row = csvReader.readLine()) != null) {
            if(row.contains(pageUrl)) return true;
        }
        csvReader.close();

        return false;
    }

}

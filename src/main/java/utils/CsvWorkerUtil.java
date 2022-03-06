package utils;


import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvWorkerUtil {

    // prebehne priecinok a vrati nazvy suborov v priecinku
    public static Set<String> listFiles(String dir) throws IOException {
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

    public static String readPageFromCSV(String pathToCsv) throws IOException {
        String retPage;
        CSVReader reader = new CSVReader(new FileReader(pathToCsv));
        List<String[]> pages;
        pages = reader.readAll();
        reader.close();
        if (pages == null) return null;
        if (CollectionUtils.isEmpty(pages)) return null;
        retPage = pages.get(0)[0];
        if (StringUtils.isBlank(retPage)) return null;
        String finalRetPage = retPage;
        List<String[]> filtered = pages.stream()
                .filter(entry -> !entry[0].equals(finalRetPage))
                .collect(Collectors.toList());
        FileWriter fw = new FileWriter(pathToCsv);
        CSVWriter w = new CSVWriter(fw);
        w.writeAll(filtered);
        w.close();
        return retPage;
    }

    public static String createDirectory(String dirName) {
        if (StringUtils.isBlank(dirName)) return null;
        dirName = dirName.replace(ConfigEnum.BASE_PATH.label, ConfigEnum.DEST_PATH.label);
        if (!dirName.contains("~")) return null;
        if (dirName.contains(".csv")) dirName = dirName.replace(".csv", "");
        dirName = dirName.replace("~", "\\");
        dirName = dirName + "\\";
        File file = new File(dirName);
        file.mkdirs();
        return dirName;
    }

    public static String createDirectory(String dirName, String sourcePath, String destPath) {
        if (StringUtils.isBlank(dirName)) return null;
        dirName = dirName.replace(sourcePath, destPath);
        if (!dirName.contains("~")) return null;
        if (dirName.contains(".csv")) dirName = dirName.replace(".csv", "");
        dirName = dirName.replace("~", "\\");
        dirName = dirName + "\\";
        File file = new File(dirName);
        file.mkdirs();
        return dirName;
    }

    public static String extractCategory(String path) {
        if (StringUtils.isBlank(path)) return null;
        String category = ConfigEnum.CSV_CATEGORIES_PATH_2.label.replace(ConfigEnum.BASE_PATH.label, "");
        category = path.replace(category, "");
        category = category.replace(ConfigEnum.DEST_PATH.label+"\\", "");
        category = category.substring(0, category.lastIndexOf('\\'));
        return category;
    }

    public static void storeScrapedUrl(String pageUrl) throws IOException {
        FileWriter csv = new FileWriter(ConfigEnum.SCRAPPED_PAGES_PATH.label, true);
        csv.append(pageUrl);
        csv.append(",");
        csv.append("\n");
        csv.close();
    }

    public static void storeScrapedUrl(String pageUrl, String category) throws IOException {
        FileWriter csv = new FileWriter(ConfigEnum.SCRAPPED_PAGES_PATH.label, true);
        String record = pageUrl + "," + category;
        csv.append(record);
        csv.append(",");
        csv.append("\n");
        csv.close();
    }


    public static void storeScrapedUrlFailed(String pageUrl) {
        try {
            FileWriter csv = new FileWriter(ConfigEnum.SCRAPPED_PAGES_PATH_FAILED.label, true);
            csv.append(pageUrl);
            csv.append(",");
            csv.append("\n");
            csv.close();
        } catch (Exception e) {
            System.out.println("Chyba pri zapisovani url");
        }
    }

    public static void storeScrapedUrlFailed(String pageUrl, String category) {
        try {
            FileWriter csv = new FileWriter(ConfigEnum.SCRAPPED_PAGES_PATH_FAILED.label, true);
            String record = pageUrl + "," + category;
            csv.append(record);
            csv.append(",");
            csv.append("\n");
            csv.close();
        } catch (Exception e) {
            System.out.println("Chyba pri zapisovani url");
        }
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
            if (row.contains(pageUrl)) return true;
        }
        csvReader.close();

        return false;
    }

    public static void removeFile(String path) {
        try {
            Files.delete(Paths.get(path));
        } catch (IOException e) {
            System.out.println("Nepodarilo sa odstranit subor !!!!!");
            e.printStackTrace();
        }
    }

}

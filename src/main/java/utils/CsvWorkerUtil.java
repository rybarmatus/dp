package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvWorkerUtil {

    // prebehne priecinok a vrati nazvy suborov v priecinku
    private static Set<String> listFiles(String dir) throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get(dir), 1)) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }

    public static Set<String> readFilesNames() throws IOException {
        Set<String> files = listFiles( ConfigEnum.CSV_DIR_PATH.label);

        return files;
    }

    public static Set<String> getPagesFromCSV(String pathToCsv) throws IOException {
        Set<String> pages = new HashSet<>();
        BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
        String row;
        while ((row = csvReader.readLine()) != null) {
//            String[] data = row.split(",");
            pages.add(row);
            // do something with the data
        }
        csvReader.close();
        return pages;
    }



}

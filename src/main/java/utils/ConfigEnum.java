package utils;

public enum ConfigEnum {

    CSV_DIR_PATH("C:\\Users\\mmatu\\Documents\\škola\\DP\\web_categories"),
    BASE_PATH("C:\\Users\\mmatu\\Documents\\škola\\DP\\"),
    SCRAPPED_PAGES_PATH("C:\\Users\\mmatu\\Documents\\škola\\DP\\scrappedUrls.csv"),
    ;
    public final String label;

    ConfigEnum(String label) {
        this.label = label;
    }
}

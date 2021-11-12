package utils;

public enum ConfigEnum {

    CSV_DIR_PATH("C:\\Users\\mmatu\\Documents\\škola\\DP\\web_categories"),
    BASE_PATH("C:\\Users\\mmatu\\Documents\\škola\\DP\\"),
    SCRAPPED_PAGES_PATH("C:\\Users\\mmatu\\Documents\\škola\\DP\\scrappedUrls.csv"),
    EXTENSION_COOKIE_1("C:\\Users\\mmatu\\Documents\\škola\\DP\\extensions\\extension_1_38_6_0.crx"),
    EXTENSION_COOKIE_2("C:\\Users\\mmatu\\Documents\\škola\\DP\\extensions\\extension_5_1_0_0.crx"),
    EXTENSION_COOKIE_3("C:\\Users\\mmatu\\Documents\\škola\\DP\\extensions\\extension_3_3_4_0.crx"),
    EXT_FIREFOX_DONT_CARE_COOKIES("C:\\Users\\mmatu\\Documents\\škola\\DP\\extensions\\dont_care_cookie.xpi"),
    EXT_FIREFOX_POPER_PATH("C:\\Users\\mmatu\\Documents\\škola\\DP\\extensions\\poper_blocker.xpi"),
    EXT_FIREFOX_UBLOCK("C:\\Users\\mmatu\\Documents\\škola\\DP\\extensions\\ublock.xpi"),
    ;
    public final String label;

    ConfigEnum(String label) {
        this.label = label;
    }
}

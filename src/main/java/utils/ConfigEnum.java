package utils;

public enum ConfigEnum {

    CSV_CATEGORIES_PATH("E:\\win\\DP\\web_categories"),
    CSV_CATEGORIES_PATH_2("E:\\win\\DP\\web_categories - Copy"),
    BASE_PATH("E:\\win\\DP"),
    DEST_PATH("E:\\dp2"),
    DEST_HTML_PATH("E:\\storedHtmls"),
    SCRAPPED_PAGES_PATH("E:\\win\\DP\\scrappedUrls.csv"),
    SCRAPPED_PAGES_PATH_FAILED("E:\\win\\DP\\scrappedUrls_fail.csv"),
    EXTENSION_COOKIE_1("C:\\Users\\mmatu\\Documents\\škola\\DP\\extensions\\extension_1_38_6_0.crx"),
    EXTENSION_COOKIE_2("C:\\Users\\mmatu\\Documents\\škola\\DP\\extensions\\extension_5_1_0_0.crx"),
    EXTENSION_COOKIE_3("C:\\Users\\mmatu\\Documents\\škola\\DP\\extensions\\extension_3_3_4_0.crx"),
    EXT_FIREFOX_DONT_CARE_COOKIES("E:\\win\\DP\\extensions\\dont_care_cookie.xpi"),
    EXT_FIREFOX_POPER_PATH("E:\\win\\DP\\extensions\\poper_blocker.xpi"),
    EXT_FIREFOX_UBLOCK("E:\\win\\DP\\extensions\\ublock.xpi"),
    ;
    public final String label;

    ConfigEnum(String label) {
        this.label = label;
    }
}

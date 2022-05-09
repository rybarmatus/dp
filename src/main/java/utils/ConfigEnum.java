package utils;

public enum ConfigEnum {

    CSV_CATEGORIES_PATH("E:\\win\\DP\\web_categories"),
    CSV_CATEGORIES_PATH_2("E:\\win\\DP\\web_categories - Copy"),
    BASE_PATH("E:\\win\\DP"),
    DEST_PATH("E:\\dp2"),
    DEST_HTML_PATH("E:\\storedHtmls"),
    SCRAPPED_PAGES_PATH("E:\\win\\DP\\scrappedUrls.csv"),
    SCRAPPED_PAGES_PATH_FAILED("E:\\win\\DP\\scrappedUrls_fail.csv"),
    EXTENSION_COOKIE_1(""),
    EXTENSION_COOKIE_2(""),
    EXTENSION_COOKIE_3(""),
    EXT_FIREFOX_DONT_CARE_COOKIES(""),
    EXT_FIREFOX_POPER_PATH(""),
    EXT_FIREFOX_UBLOCK(""),
    EXT_FIREFOX_POPUPOFF("E:\\ext\\popupoff-2.0.3-fx.xpi"),
    ;
    public final String label;

    ConfigEnum(String label) {
        this.label = label;
    }
}

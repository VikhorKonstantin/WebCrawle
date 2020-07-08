package by.vikhor.softeqdemo.webcrawler

class TestConstants {
    public static final LINK = "https://www.w3schools.com"

    public static String HTML_DOC_WITH_A_LINK = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<body>\n" +
            "\n" +
            "<h2>HTML Links</h2>\n" +
            "<p>HTML links are defined with the a tag:</p>\n" +
            "\n" +
            "<a href=" + LINK + ">This is a link</a>\n" +
            "\n" +
            "</body>\n" +
            "</html>"

    public static String HTML_DOCK_WITHOUT_LINK = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<body>\n" +
            "\n" +
            "<h2>HTML Buttons</h2>\n" +
            "<p>HTML buttons are defined with the button tag:</p>\n" +
            "\n" +
            "<button>Click me</button>\n" +
            "\n" +
            "</body>\n" +
            "</html>"
}

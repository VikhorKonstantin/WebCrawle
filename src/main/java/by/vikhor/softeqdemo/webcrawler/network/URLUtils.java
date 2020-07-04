package by.vikhor.softeqdemo.webcrawler.network;

import lombok.SneakyThrows;

import java.net.URI;

public class URLUtils {

    @SneakyThrows
    public static String normalizeUrl(String url) {
        return new URI(url).normalize().toURL().toString();
    }
}

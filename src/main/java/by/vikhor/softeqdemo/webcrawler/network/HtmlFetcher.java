package by.vikhor.softeqdemo.webcrawler.network;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HtmlFetcher {

    public String fetchPageContent(String url) throws IOException {
        return Jsoup.connect(url).get().body().html();
    }
}

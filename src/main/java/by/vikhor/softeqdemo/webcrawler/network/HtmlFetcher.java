package by.vikhor.softeqdemo.webcrawler.network;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class HtmlFetcher {

    private final WebClient webClient;

    public HtmlFetcher(WebClient.Builder webClientBuilder) {
        webClient = webClientBuilder
                .defaultHeader(NetworkConstants.CONTENT_TYPE_HEADER, MediaType.TEXT_HTML_VALUE)
                .build();
    }


    public String fetchPageContent(String url) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}

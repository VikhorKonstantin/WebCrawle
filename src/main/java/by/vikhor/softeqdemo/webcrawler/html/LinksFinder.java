package by.vikhor.softeqdemo.webcrawler.html;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LinksFinder {

    public List<String> findAllLinks(String htmlDocument) {
        return Jsoup.parse(htmlDocument)
                .body()
                .select(HtmlConstants.HREF_SELECTOR)
                .stream()
                .map(e -> e.attr(HtmlConstants.ABSOLUTE_URL_KEY))
                .filter(UrlValidator.getInstance()::isValid)
                .collect(Collectors.toList());
    }

}

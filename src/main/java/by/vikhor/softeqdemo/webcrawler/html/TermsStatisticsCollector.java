package by.vikhor.softeqdemo.webcrawler.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TermsStatisticsCollector {

    /**
     * Returns {@link Map} with term value as a Map key and number of term hits as a Map key
     *
     * @param htmlDoc Document to find terms in
     * @return {@link Map} with term value as a Map key and number of term hits as a Map key
     */
    public Map<String, Integer> collectTermsStatistics(String htmlDoc, Set<String> terms) {
        Element body = Jsoup.parse(htmlDoc).body();
        return terms.stream()
                .map(t -> collectTermStatistics(t, body))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map.Entry<String, Integer> collectTermStatistics(String t, Element body) {
        int numberOfHits = body.select(String.format(HtmlConstants.CONTAINS_OWN_SELECTOR_TEMPLATE, t))
                .size();
        return Map.entry(t, numberOfHits);
    }
}

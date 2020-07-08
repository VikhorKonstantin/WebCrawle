package by.vikhor.softeqdemo.webcrawler.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TermsStatisticsCollector {

    private static final Logger LOGGER = LoggerFactory.getLogger(TermsStatisticsCollector.class);

    /**
     * Returns {@link Map} with term value as a Map key and number of term hits as a Map key
     *
     * @param htmlDocString String representation of the ocument to find terms in
     * @return {@link Map} with term value as a Map key and number of term hits as a Map key
     */
    public Map<String, Integer> collectTermsStatistics(String htmlDocString, Set<String> terms) {
        Element htmlDocBody = Jsoup.parse(htmlDocString).body();
        Map<String, Integer> statistics = terms.stream()
                .map(t -> collectTermStatistics(t, htmlDocBody))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        logStatistics(statistics);
        return statistics;
    }

    private void logStatistics(Map<String, Integer> statistics) {
        String logValue = statistics.entrySet().stream()
                .map(e -> String.format("%s : %d", e.getKey(), e.getValue()))
                .collect(Collectors.joining("; "));
        LOGGER.debug(logValue);
    }

    private Map.Entry<String, Integer> collectTermStatistics(String t, Element htmlDocBody) {
        int numberOfHits = htmlDocBody.select(String.format(HtmlConstants.CONTAINS_OWN_SELECTOR_TEMPLATE, t))
                .size();
        return Map.entry(t, numberOfHits);
    }
}

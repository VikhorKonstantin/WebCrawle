package by.vikhor.softeqdemo.webcrawler.crawler;

import by.vikhor.softeqdemo.webcrawler.html.LinksFinder;
import by.vikhor.softeqdemo.webcrawler.html.TermsStatisticsCollector;
import by.vikhor.softeqdemo.webcrawler.network.HtmlFetcher;
import by.vikhor.softeqdemo.webcrawler.network.URLUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.RecursiveTask;
import java.util.function.BiFunction;

final class CrawlerTask extends RecursiveTask<Map<String, Integer>> {

    private final String normalizedPageUrl;
    private final LinksFinder linksFinder;
    private final TermsStatisticsCollector termsStatisticsCollector;
    private final Set<String> terms;
    private final HtmlFetcher htmlFetcher;
    private final Integer currentDepth;
    private final Set<String> crawled;

    public CrawlerTask(String pageUrl, LinksFinder linksFinder, TermsStatisticsCollector termsStatisticsCollector,
                       Set<String> terms, HtmlFetcher htmlFetcher, Integer currentDepth, Set<String> crawled) {
        this.normalizedPageUrl = URLUtils.normalizeUrl(pageUrl);
        this.linksFinder = linksFinder;
        this.termsStatisticsCollector = termsStatisticsCollector;
        this.terms = terms;
        this.htmlFetcher = htmlFetcher;
        this.currentDepth = currentDepth;
        this.crawled = crawled;
    }


    @Override
    public Map<String, Integer> compute() {
        crawled.add(normalizedPageUrl);
        String pageContent = fetchPageContentOrEmptyString();
        Map<String, Integer> termsStatistics = termsStatisticsCollector.collectTermsStatistics(pageContent, terms);
        if (currentDepth == 2) {
            return termsStatistics;
        }
        mergeSubstatistics(pageContent, termsStatistics);
        return termsStatistics;
    }

    private String fetchPageContentOrEmptyString() {
        try {
            return htmlFetcher.fetchPageContent(normalizedPageUrl);
        } catch (IOException e) {
            return "";
        }
    }

    private void mergeSubstatistics(String pageContent, Map<String, Integer> termsStatistics) {
        List<String> allLinks = linksFinder.findAllLinks(pageContent);
        allLinks.stream()
                .filter(l -> !crawled.contains(l))
                .map(this::forkAndJoin)
                .forEach(subStatistics -> {
                    for (var term : terms) {
                        termsStatistics.compute(term, accumulateHitsNumber(subStatistics));
                    }
                });
    }

    private BiFunction<String, Integer, Integer> accumulateHitsNumber(Map<String, Integer> subStatistics) {
        return (key, oldValue) -> oldValue + subStatistics.getOrDefault(key, 0);
    }

    private Map<String, Integer> forkAndJoin(String link) {
        CrawlerTask subTask = new CrawlerTask(link, linksFinder,
                termsStatisticsCollector, terms, htmlFetcher, currentDepth + 1, crawled);
        subTask.fork();
        return subTask.join();
    }
}

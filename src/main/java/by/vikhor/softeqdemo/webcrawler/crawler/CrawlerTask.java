package by.vikhor.softeqdemo.webcrawler.crawler;

import by.vikhor.softeqdemo.webcrawler.entity.CrawlingParams;
import by.vikhor.softeqdemo.webcrawler.html.LinksFinder;
import by.vikhor.softeqdemo.webcrawler.html.TermsStatisticsCollector;
import by.vikhor.softeqdemo.webcrawler.network.HtmlFetcher;
import by.vikhor.softeqdemo.webcrawler.network.URLUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;

final class CrawlerTask extends RecursiveTask<Map<String, Integer>> {
    private final CrawlingParams crawlingParams;
    private final LinksFinder linksFinder;
    private final TermsStatisticsCollector termsStatisticsCollector;
    private final HtmlFetcher htmlFetcher;
    private final Integer currentDepth;
    private final Set<String> crawled;
    private final AtomicLong commonNumberOfVisitedPages;

    public CrawlerTask(CrawlingParams crawlingParams, LinksFinder linksFinder,
                       TermsStatisticsCollector termsStatisticsCollector,
                       HtmlFetcher htmlFetcher, Integer currentDepth, Set<String> crawled,
                       AtomicLong commonNumberOfVisitedLinks) {
        this.crawlingParams = crawlingParams;
        this.linksFinder = linksFinder;
        this.termsStatisticsCollector = termsStatisticsCollector;
        this.htmlFetcher = htmlFetcher;
        this.currentDepth = currentDepth;
        this.crawled = crawled;
        this.commonNumberOfVisitedPages = commonNumberOfVisitedLinks;
    }

    @Override
    public Map<String, Integer> compute() {
        String pageContent = fetchPageContentOrEmptyString();
        crawled.add(crawlingParams.getSeedUrl());
        Map<String, Integer> termsStatistics =
                termsStatisticsCollector.collectTermsStatistics(pageContent, crawlingParams.getTerms());
        if (commonNumberOfVisitedPages.incrementAndGet() >= crawlingParams.getMaxNumberOfVisitedPages()
                || currentDepth.equals(crawlingParams.getDepth())) {
            return termsStatistics;
        }
        mergeSubstatistics(pageContent, termsStatistics);
        return termsStatistics;
    }

    private String fetchPageContentOrEmptyString() {
        try {
            return htmlFetcher.fetchPageContent(crawlingParams.getSeedUrl());
        } catch (IOException e) {
            return "";
        }
    }

    private void mergeSubstatistics(String pageContent, Map<String, Integer> termsStatistics) {
        List<String> allLinks = linksFinder.findAllLinks(pageContent);
        long limit = crawlingParams.getMaxNumberOfVisitedPages() - commonNumberOfVisitedPages.get();
        limit = limit < 0 ? 0 : limit;
        allLinks.stream()
                .unordered()
                .limit(limit)
                .filter(l -> !crawled.contains(l))
                .map(this::forkAndJoin)
                .forEach(subStatistics -> {
                    for (var term : crawlingParams.getTerms()) {
                        termsStatistics.compute(term, accumulateHitsNumber(subStatistics));
                    }
                });
    }

    private BiFunction<String, Integer, Integer> accumulateHitsNumber(Map<String, Integer> subStatistics) {
        return (key, oldValue) -> oldValue + subStatistics.getOrDefault(key, 0);
    }

    private Map<String, Integer> forkAndJoin(String link) {
        CrawlerTask subTask = new CrawlerTask(prepareCrawlingParams(link),
                linksFinder, termsStatisticsCollector, htmlFetcher,
                currentDepth + 1, crawled, commonNumberOfVisitedPages);
        subTask.fork();
        return subTask.join();
    }

    private CrawlingParams prepareCrawlingParams(String link) {
        return new CrawlingParams(URLUtils.normalizeUrl(link), crawlingParams.getDepth(),
                crawlingParams.getMaxNumberOfVisitedPages(),
                crawlingParams.getTerms());
    }
}

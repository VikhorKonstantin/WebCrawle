package by.vikhor.softeqdemo.webcrawler.crawler;

import by.vikhor.softeqdemo.webcrawler.html.LinksFinder;
import by.vikhor.softeqdemo.webcrawler.html.TermsStatisticsCollector;
import by.vikhor.softeqdemo.webcrawler.network.HtmlFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CrawlerTaskFactory {
    private final LinksFinder linksFinder;
    private final TermsStatisticsCollector termsStatisticsCollector;
    private final Set<String> terms;
    private final HtmlFetcher htmlFetcher;

    @Autowired
    public CrawlerTaskFactory(LinksFinder linksFinder, TermsStatisticsCollector termsStatisticsCollector,
                              Set<String> terms, HtmlFetcher htmlFetcher) {
        this.linksFinder = linksFinder;
        this.termsStatisticsCollector = termsStatisticsCollector;
        this.terms = terms;
        this.htmlFetcher = htmlFetcher;
    }

    public CrawlerTask createCrawlerTask(String pageUrl, Integer depthLevel) {
        return new CrawlerTask(pageUrl, linksFinder, termsStatisticsCollector,
                terms, htmlFetcher, depthLevel, ConcurrentHashMap.newKeySet());
    }
}

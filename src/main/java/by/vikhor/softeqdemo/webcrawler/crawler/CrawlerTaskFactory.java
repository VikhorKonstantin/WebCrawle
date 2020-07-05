package by.vikhor.softeqdemo.webcrawler.crawler;

import by.vikhor.softeqdemo.webcrawler.entity.CrawlingParams;
import by.vikhor.softeqdemo.webcrawler.html.LinksFinder;
import by.vikhor.softeqdemo.webcrawler.html.TermsStatisticsCollector;
import by.vikhor.softeqdemo.webcrawler.network.HtmlFetcher;
import by.vikhor.softeqdemo.webcrawler.network.URLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class CrawlerTaskFactory {
    private final LinksFinder linksFinder;
    private final TermsStatisticsCollector termsStatisticsCollector;
    private final HtmlFetcher htmlFetcher;

    @Autowired
    public CrawlerTaskFactory(LinksFinder linksFinder, TermsStatisticsCollector termsStatisticsCollector,
                              HtmlFetcher htmlFetcher) {
        this.linksFinder = linksFinder;
        this.termsStatisticsCollector = termsStatisticsCollector;
        this.htmlFetcher = htmlFetcher;
    }

    public CrawlerTask createCrawlerTask(CrawlingParams crawlingParams) {
        crawlingParams.setSeedUrl(URLUtils.normalizeUrl(crawlingParams.getSeedUrl()));
        return new CrawlerTask(crawlingParams, linksFinder,
                termsStatisticsCollector, htmlFetcher, 1,
                ConcurrentHashMap.newKeySet(), new AtomicLong(0));
    }
}

package by.vikhor.softeqdemo.webcrawler;

import by.vikhor.softeqdemo.webcrawler.crawler.CrawlerTaskFactory;
import by.vikhor.softeqdemo.webcrawler.entity.CrawlingParams;
import by.vikhor.softeqdemo.webcrawler.network.URLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Component
public class WebCrawlerRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebCrawlerRunner.class);

    @Autowired
    private CrawlerTaskFactory crawlerTaskFactory;

    @Override
    public void run(String... args) throws Exception {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        CrawlingParams crawlingParams =
                new CrawlingParams(URLUtils.normalizeUrl("http://softeq.by/"), 2, 10000L,
                        Set.of("Java", "Junior"));
        Map<String, Integer> statistics = forkJoinPool.invoke(
                crawlerTaskFactory.createCrawlerTask(crawlingParams)
        );
        logStatistics(statistics);
    }

    private void logStatistics(Map<String, Integer> statitics) {
        String logValue = statitics.entrySet().stream()
                .map(e -> String.format("%s : %d", e.getKey(), e.getValue()))
                .collect(Collectors.joining("; "));
        LOGGER.info(logValue);
    }
}

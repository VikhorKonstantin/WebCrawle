package by.vikhor.softeqdemo.webcrawler;

import by.vikhor.softeqdemo.webcrawler.crawler.CrawlerTaskFactory;
import by.vikhor.softeqdemo.webcrawler.network.URLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ForkJoinPool;

@Component
public class WebCrawlerRunner implements CommandLineRunner {
    @Autowired
    private CrawlerTaskFactory crawlerTaskFactory;

    @Override
    public void run(String... args) throws Exception {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        Map<String, Integer> statistics = forkJoinPool.invoke(
                crawlerTaskFactory.createCrawlerTask(URLUtils.normalizeUrl("https://en.wikipedia.org/wiki/Elon_Musk"),
                        1)
        );
        statistics.forEach((key, value) -> System.out.println(String.format("%s : %d", key, value)));
    }
}

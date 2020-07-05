package by.vikhor.softeqdemo.webcrawler.service;

import by.vikhor.softeqdemo.webcrawler.crawler.CrawlingTaskFactory;
import by.vikhor.softeqdemo.webcrawler.entity.CrawlingParams;
import by.vikhor.softeqdemo.webcrawler.entity.CrawlingStatus;
import by.vikhor.softeqdemo.webcrawler.entity.TermsStatistics;
import by.vikhor.softeqdemo.webcrawler.exception.StatisticsNotFoundException;
import by.vikhor.softeqdemo.webcrawler.network.URLUtils;
import by.vikhor.softeqdemo.webcrawler.repository.TermsStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

@Service
public class CrawlingServiceImpl implements CrawlingService {

    private final TermsStatisticsRepository termsStatisticsRepository;
    private final ForkJoinPool forkJoinPool;
    private final CrawlingTaskFactory crawlingTaskFactory;

    @Autowired
    public CrawlingServiceImpl(TermsStatisticsRepository termsStatisticsRepository, ForkJoinPool forkJoinPool,
                               CrawlingTaskFactory crawlingTaskFactory) {
        this.termsStatisticsRepository = termsStatisticsRepository;
        this.forkJoinPool = forkJoinPool;
        this.crawlingTaskFactory = crawlingTaskFactory;
    }

    @Override
    public TermsStatistics startCrawling(CrawlingParams crawlingParams) {
        crawlingParams.setSeedUrl(URLUtils.normalizeUrl(crawlingParams.getSeedUrl()));
        TermsStatistics termsStatistics = new TermsStatistics();
        termsStatistics.setCrawlingStatus(CrawlingStatus.IN_PROGRESS);
        termsStatistics.setSeedUrl(crawlingParams.getSeedUrl());
        termsStatistics = termsStatisticsRepository.save(termsStatistics);
        final String id = termsStatistics.getTermsStatisticsId();
        CompletableFuture.supplyAsync(
                () -> forkJoinPool.invoke(crawlingTaskFactory.createCrawlingTask(crawlingParams))
        )
                .thenAcceptAsync(statistics -> {
                    TermsStatistics completed = termsStatisticsRepository.findById(id)
                            .orElseThrow(StatisticsNotFoundException::new);
                    completed.setStatistics(statistics);
                    completed.setCrawlingStatus(CrawlingStatus.DONE);
                    termsStatisticsRepository.save(completed);
                });
        return termsStatistics;
    }

    @Override
    public TermsStatistics getTermsStatistics(String id) {
        return termsStatisticsRepository.findById(id).orElseThrow(StatisticsNotFoundException::new);
    }
}

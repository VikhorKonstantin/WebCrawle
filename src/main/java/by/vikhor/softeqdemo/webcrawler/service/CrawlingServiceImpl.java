package by.vikhor.softeqdemo.webcrawler.service;

import by.vikhor.softeqdemo.webcrawler.crawler.CrawlingTaskFactory;
import by.vikhor.softeqdemo.webcrawler.csv.CsvTotalStatisticsWriter;
import by.vikhor.softeqdemo.webcrawler.entity.CrawlingParams;
import by.vikhor.softeqdemo.webcrawler.entity.CrawlingStatus;
import by.vikhor.softeqdemo.webcrawler.entity.TermToHitsPair;
import by.vikhor.softeqdemo.webcrawler.entity.TermsStatistics;
import by.vikhor.softeqdemo.webcrawler.exception.FileWritingException;
import by.vikhor.softeqdemo.webcrawler.exception.StatisticsNotFoundException;
import by.vikhor.softeqdemo.webcrawler.network.URLUtils;
import by.vikhor.softeqdemo.webcrawler.repository.TermsStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
public class CrawlingServiceImpl implements CrawlingService {

    private final TermsStatisticsRepository termsStatisticsRepository;
    private final ForkJoinPool forkJoinPool;
    private final CrawlingTaskFactory crawlingTaskFactory;
    private final CsvTotalStatisticsWriter csvTotalStatisticsWriter;

    @Autowired
    public CrawlingServiceImpl(TermsStatisticsRepository termsStatisticsRepository, ForkJoinPool forkJoinPool,
                               CrawlingTaskFactory crawlingTaskFactory,
                               CsvTotalStatisticsWriter csvTotalStatisticsWriter) {
        this.termsStatisticsRepository = termsStatisticsRepository;
        this.forkJoinPool = forkJoinPool;
        this.crawlingTaskFactory = crawlingTaskFactory;
        this.csvTotalStatisticsWriter = csvTotalStatisticsWriter;
    }

    @Override
    public TermsStatistics startCrawling(CrawlingParams crawlingParams) {
        crawlingParams.setSeedUrl(URLUtils.normalizeUrl(crawlingParams.getSeedUrl()));
        TermsStatistics termsStatistics = new TermsStatistics();
        termsStatistics.setCrawlingStatus(CrawlingStatus.IN_PROGRESS);
        termsStatistics.setSeedUrl(crawlingParams.getSeedUrl());
        termsStatistics = termsStatisticsRepository.save(termsStatistics);
        final String id = termsStatistics.getTermsStatisticsId();
        CompletableFuture
                .supplyAsync(() -> forkJoinPool.invoke(crawlingTaskFactory.createCrawlingTask(crawlingParams)))
                .thenAcceptAsync(statistics -> {
                    TermsStatistics completed = termsStatisticsRepository.findById(id)
                            .orElseThrow(StatisticsNotFoundException::new);
                    completed.setTermToHitsPairs(convertMapToTermToHitsList(statistics));
                    completed.setCrawlingStatus(CrawlingStatus.DONE);
                    termsStatisticsRepository.save(completed);
                });
        return termsStatistics;
    }


    @Override
    public TermsStatistics getTermsStatistics(String id) {
        return termsStatisticsRepository.findById(id).orElseThrow(StatisticsNotFoundException::new);
    }

    @Override
    public byte[] getTotalTermsStatisticsCsvFileBytes(Set<String> terms) throws FileWritingException {
        List<TermsStatistics> termsStatisticsList =
                termsStatisticsRepository.findAllByTerms(terms);
        return getCsvFileBytes(terms, termsStatisticsList);
    }

    @Override
    public byte[] getTopTermsStatisticsCsvFileBytes(Set<String> terms, Integer limit) throws FileWritingException {
        List<TermsStatistics> topByTerms = termsStatisticsRepository.findTopByTerms(terms, limit);
        return getCsvFileBytes(terms, topByTerms);
    }

    /**
     * Required to provide proper storage format,
     * because Spring Data MongoDB stores Maps as a document, that is not convenient.
     */
    private List<TermToHitsPair> convertMapToTermToHitsList(Map<String, Integer> statistics) {
        return statistics.entrySet().stream()
                .map(e -> new TermToHitsPair(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    private byte[] getCsvFileBytes(Set<String> terms, List<TermsStatistics> termsStatisticsList) throws FileWritingException {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream();
             OutputStreamWriter streamWriter = new OutputStreamWriter(stream, StandardCharsets.UTF_8)) {
            csvTotalStatisticsWriter.writeTotalStatistics(termsStatisticsList, terms, streamWriter);
            streamWriter.flush();
            return stream.toByteArray();
        } catch (IOException e) {
            throw new FileWritingException(e.getMessage(), e);
        }
    }
}

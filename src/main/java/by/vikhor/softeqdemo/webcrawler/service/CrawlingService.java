package by.vikhor.softeqdemo.webcrawler.service;

import by.vikhor.softeqdemo.webcrawler.entity.CrawlingParams;
import by.vikhor.softeqdemo.webcrawler.entity.TermsStatistics;
import by.vikhor.softeqdemo.webcrawler.exception.FileWritingException;
import by.vikhor.softeqdemo.webcrawler.exception.StatisticsNotFoundException;

import java.util.Set;

public interface CrawlingService {

    TermsStatistics startCrawling(CrawlingParams crawlingParams);

    TermsStatistics getTermsStatistics(String id) throws StatisticsNotFoundException;

    byte[] getTotalTermsStatisticsCsvFileBytes(Set<String> terms) throws FileWritingException;

    byte[] getTopTermsStatisticsCsvFileBytes(Set<String> terms, Integer limit) throws FileWritingException;
}

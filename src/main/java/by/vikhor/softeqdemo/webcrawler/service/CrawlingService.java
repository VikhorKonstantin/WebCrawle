package by.vikhor.softeqdemo.webcrawler.service;

import by.vikhor.softeqdemo.webcrawler.entity.CrawlingParams;
import by.vikhor.softeqdemo.webcrawler.entity.TermsStatistics;
import by.vikhor.softeqdemo.webcrawler.exception.FileWritingException;

import java.util.Set;

public interface CrawlingService {

    TermsStatistics startCrawling(CrawlingParams crawlingParams);

    TermsStatistics getTermsStatistics(String id);

    byte[] getTotalTermsStatistics(Set<String> terms) throws FileWritingException;
}

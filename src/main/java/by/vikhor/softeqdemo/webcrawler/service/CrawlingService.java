package by.vikhor.softeqdemo.webcrawler.service;

import by.vikhor.softeqdemo.webcrawler.entity.CrawlingParams;
import by.vikhor.softeqdemo.webcrawler.entity.TermsStatistics;

public interface CrawlingService {

    TermsStatistics startCrawling(CrawlingParams crawlingParams);

    TermsStatistics getTermsStatistics(String id);

}

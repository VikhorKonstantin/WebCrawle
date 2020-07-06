package by.vikhor.softeqdemo.webcrawler.repository;

import by.vikhor.softeqdemo.webcrawler.entity.TermsStatistics;

import java.util.List;
import java.util.Set;

public interface CustomTermsStatisticRepository {

    List<TermsStatistics> findAllByTerms(Set<String> terms);
    List<TermsStatistics> findTopByTerms(Set<String> terms, Integer limit);
}

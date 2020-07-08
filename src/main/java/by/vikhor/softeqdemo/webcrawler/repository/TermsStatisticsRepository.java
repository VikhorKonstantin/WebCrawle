package by.vikhor.softeqdemo.webcrawler.repository;

import by.vikhor.softeqdemo.webcrawler.entity.TermsStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsStatisticsRepository
        extends MongoRepository<TermsStatistics, String>, CustomTermsStatisticRepository {
}

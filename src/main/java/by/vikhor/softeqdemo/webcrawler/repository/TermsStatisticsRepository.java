package by.vikhor.softeqdemo.webcrawler.repository;

import by.vikhor.softeqdemo.webcrawler.entity.TermsStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TermsStatisticsRepository
        extends MongoRepository<TermsStatistics, String>, CustomTermsStatisticRepository {
}

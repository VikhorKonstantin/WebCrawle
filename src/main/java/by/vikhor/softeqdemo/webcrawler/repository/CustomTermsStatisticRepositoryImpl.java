package by.vikhor.softeqdemo.webcrawler.repository;

import by.vikhor.softeqdemo.webcrawler.entity.TermsStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class CustomTermsStatisticRepositoryImpl implements CustomTermsStatisticRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CustomTermsStatisticRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<TermsStatistics> findAllByTerms(Set<String> terms) {
        Query query = new Query();
        query.addCriteria(createByTermsCriteria(terms));
        return mongoTemplate.find(query, TermsStatistics.class);
    }

    private Criteria createByTermsCriteria(Set<String> terms) {
        return Criteria.where("termToHitsPairs.term").all(terms);
    }

    @Override
    public List<TermsStatistics> findTopByTerms(Set<String> terms, Integer limit) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(createByTermsCriteria(terms)),
                Aggregation.project(TermsStatistics.class)
                        .andExpression("{ $sum : \"termToHitsPairs.numberOfHits\" }").as("summ"),
                Aggregation.sort(Sort.by(Sort.Direction.ASC, "summ")),
                Aggregation.limit(limit.longValue()));

        return mongoTemplate.aggregate(aggregation, TermsStatistics.class, TermsStatistics.class).getMappedResults();
    }

}

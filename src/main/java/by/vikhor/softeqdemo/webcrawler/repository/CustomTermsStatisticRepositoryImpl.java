package by.vikhor.softeqdemo.webcrawler.repository;

import by.vikhor.softeqdemo.webcrawler.entity.TermsStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
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
        query.addCriteria(Criteria.where("termToHitsPairs.term").all(terms));
        return mongoTemplate.find(query, TermsStatistics.class);
    }

    @Override
    public List<TermsStatistics> findTopByTerms(Set<String> terms, Integer limit) {
        Query query = new Query();

        return Collections.emptyList();
    }

}

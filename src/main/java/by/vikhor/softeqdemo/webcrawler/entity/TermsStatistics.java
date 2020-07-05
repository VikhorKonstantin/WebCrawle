package by.vikhor.softeqdemo.webcrawler.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TermsStatistics {
    @Id
    private String termsStatisticsId;
    private CrawlingStatus crawlingStatus;
    private Map<String, Integer> statistics;
    private String seedUrl;
}

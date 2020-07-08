package by.vikhor.softeqdemo.webcrawler.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TermsStatistics {
    @CreatedDate
    public LocalDateTime createdDate;
    @Id
    private String termsStatisticsId;
    private CrawlingStatus crawlingStatus;
    private List<TermToHitsPair> termToHitsPairs;
    private String seedUrl;
}

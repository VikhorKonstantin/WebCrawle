package by.vikhor.softeqdemo.webcrawler.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrawlingParams {

    private String seedUrl;
    private Integer depth;
    private Long maxNumberOfVisitedPages;
    private Set<String> terms;

}

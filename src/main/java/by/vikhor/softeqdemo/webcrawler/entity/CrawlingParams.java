package by.vikhor.softeqdemo.webcrawler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CrawlingParams {

    private String seedUrl;
    private Integer depth;
    private Long maxNumberOfVisitedPages;
    private Set<String> terms;

}

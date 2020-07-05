package by.vikhor.softeqdemo.webcrawler.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrawlingParams {

    @NotBlank
    private String seedUrl;
    @Positive
    private Integer depth;
    @Positive
    private Long maxNumberOfVisitedPages;
    @NotEmpty
    private Set<@NotBlank String> terms;

}

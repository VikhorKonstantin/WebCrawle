package by.vikhor.softeqdemo.webcrawler.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TermToHitsPair {
    private String term;
    private Integer numberOfHits;
}

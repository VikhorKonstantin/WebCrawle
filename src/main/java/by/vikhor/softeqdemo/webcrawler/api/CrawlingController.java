package by.vikhor.softeqdemo.webcrawler.api;

import by.vikhor.softeqdemo.webcrawler.entity.CrawlingParams;
import by.vikhor.softeqdemo.webcrawler.entity.TermsStatistics;
import by.vikhor.softeqdemo.webcrawler.service.CrawlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping(value = "/api/crawling")
@Validated
public class CrawlingController {

    private final CrawlingService crawlingService;

    @Autowired
    public CrawlingController(CrawlingService crawlingService) {
        this.crawlingService = crawlingService;
    }

    @PostMapping
    public ResponseEntity<TermsStatistics> startCrawling(@RequestBody @Valid CrawlingParams crawlingParams) {
        return ResponseEntity.ok(crawlingService.startCrawling(crawlingParams));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TermsStatistics> getTermsStatistics(@PathVariable @NotBlank String id) {
        return ResponseEntity.ok(crawlingService.getTermsStatistics(id));
    }
}

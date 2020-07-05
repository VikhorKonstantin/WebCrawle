package by.vikhor.softeqdemo.webcrawler.api;

import by.vikhor.softeqdemo.webcrawler.entity.CrawlingParams;
import by.vikhor.softeqdemo.webcrawler.entity.TermsStatistics;
import by.vikhor.softeqdemo.webcrawler.service.CrawlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/crawling")
public class CrawlingController {

    private final CrawlingService crawlingService;

    @Autowired
    public CrawlingController(CrawlingService crawlingService) {
        this.crawlingService = crawlingService;
    }

    @PostMapping
    public ResponseEntity<TermsStatistics> startCrawling(@RequestBody CrawlingParams crawlingParams) {
        return ResponseEntity.ok(crawlingService.startCrawling(crawlingParams));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TermsStatistics> getTermsStatistics(@PathVariable String id) {
        return ResponseEntity.ok(crawlingService.getTermsStatistics(id));
    }
}

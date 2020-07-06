package by.vikhor.softeqdemo.webcrawler.api;

import by.vikhor.softeqdemo.webcrawler.entity.CrawlingParams;
import by.vikhor.softeqdemo.webcrawler.entity.TermsStatistics;
import by.vikhor.softeqdemo.webcrawler.service.CrawlingService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Set;

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

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TermsStatistics> getTermsStatistics(@PathVariable @NotBlank String id) {
        return ResponseEntity.ok(crawlingService.getTermsStatistics(id));
    }

    @SneakyThrows
    @GetMapping
    public ResponseEntity<ByteArrayResource> getTotalTermsStatistics(@RequestParam Set<String> terms) {
        byte[] totalTermsStatisticsBytes = crawlingService.getTotalTermsStatistics(terms);
        return ResponseEntity
                .ok()
                .headers(buildTotalStatisticsResponseHeaders())
                .contentLength(totalTermsStatisticsBytes.length)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new ByteArrayResource(totalTermsStatisticsBytes));
    }

    private HttpHeaders buildTotalStatisticsResponseHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Content-Disposition", "attachment; filename*=utf-8''"
                + "totalStatistics.csv");
        return headers;
    }
}

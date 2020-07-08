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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
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

    @SneakyThrows
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TermsStatistics> getTermsStatistics(@PathVariable @NotBlank String id) {
        return ResponseEntity.ok(crawlingService.getTermsStatistics(id));
    }

    @SneakyThrows
    @GetMapping
    public ResponseEntity<ByteArrayResource> getTermsStatistics(
            @RequestParam @NotEmpty Set<@NotBlank String> terms,
            @RequestParam(required = false) @Positive Integer limit) {
        byte[] csvFileBytes = limit == null ? crawlingService.getTotalTermsStatisticsCsvFileBytes(terms)
                : crawlingService.getTopTermsStatisticsCsvFileBytes(terms, limit);
        String fileName = limit == null ? "totalStatistics"
                : "top" + limit;
        return ResponseEntity
                .ok()
                .headers(buildTotalStatisticsResponseHeaders(fileName))
                .contentLength(csvFileBytes.length)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new ByteArrayResource(csvFileBytes));
    }

    private HttpHeaders buildTotalStatisticsResponseHeaders(String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Content-Disposition", "attachment; filename*=utf-8''"
                + fileName + ".csv");
        return headers;
    }
}

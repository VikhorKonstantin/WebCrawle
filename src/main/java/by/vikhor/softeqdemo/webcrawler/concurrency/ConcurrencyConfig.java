package by.vikhor.softeqdemo.webcrawler.concurrency;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ForkJoinPool;

@Configuration
public class ConcurrencyConfig {

    @Value("${concurrency.parallelismLevel}")
    private Integer parallelismLevel;

    @Bean
    public ForkJoinPool forkJoinPool() {
        return new ForkJoinPool(parallelismLevel);
    }
}

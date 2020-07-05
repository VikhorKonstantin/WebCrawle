package by.vikhor.softeqdemo.webcrawler.concurrency;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ForkJoinPool;

@Configuration
public class ConcurrencyConfig {

    @Bean
    public ForkJoinPool forkJoinPool() {
        return new ForkJoinPool(4);
    }
}

package by.vikhor.softeqdemo.webcrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
public class WebcrawlerApplication {

    @Bean
    Set<String> terms() {
        return Set.of("Musk", "Tesla");
    }

    public static void main(String[] args) {
        SpringApplication.run(WebcrawlerApplication.class, args);
    }

}

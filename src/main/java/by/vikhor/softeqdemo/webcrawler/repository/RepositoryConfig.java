package by.vikhor.softeqdemo.webcrawler.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Configuration
@EnableMongoAuditing
public class RepositoryConfig {


    @Bean
    public Converter<Date, LocalDateTime> localDateTimeConverter() {
        return new Converter<Date, LocalDateTime>() {
            @Override
            public LocalDateTime convert(Date source) {
                return LocalDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
            }
        };
    }
}

package by.vikhor.softeqdemo.webcrawler.csv;

import by.vikhor.softeqdemo.webcrawler.entity.TermsStatistics;
import com.opencsv.CSVWriter;

import java.io.Writer;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class CsvTotalStatisticsWriter {

    public void writeTotalStatistics(List<TermsStatistics> statisticsList, Set<String> terms, Writer writer) {
        CSVWriter csvWriter = new CSVWriter(writer, ';', '\"', '\\', "\n");
        String[] header = Stream.concat(Stream.of("SeedUrl"), terms.stream()).toArray(String[]::new);
        csvWriter.writeNext(header);
        statisticsList.stream().map(
                s -> Stream.concat(Stream.of(s.getSeedUrl()),
                        terms.stream().map(t -> s.getStatistics().get(t).toString())).toArray(String[]::new)
        ).forEach(data -> csvWriter.writeNext(data, false));
    }
}

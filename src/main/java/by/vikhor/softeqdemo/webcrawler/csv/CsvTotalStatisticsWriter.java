package by.vikhor.softeqdemo.webcrawler.csv;

import by.vikhor.softeqdemo.webcrawler.entity.TermToHitsPair;
import by.vikhor.softeqdemo.webcrawler.entity.TermsStatistics;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Component;

import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Component
public class CsvTotalStatisticsWriter {

    public void writeTotalStatistics(List<TermsStatistics> termsStatisticsList, Set<String> terms, Writer writer) {
        CSVWriter csvWriter = new CSVWriter(writer, CsvConstants.CSV_SEPARATOR, CsvConstants.CSV_QUOTECHAR,
                CsvConstants.CSV_ESCAPECHAR, CsvConstants.CSV_LINE_END);
        String[] header = Stream.concat(Stream.of("SeedUrl", "CreatedAt"), terms.stream()).toArray(String[]::new);
        csvWriter.writeNext(header);
        termsStatisticsList.stream()
                .map(s -> Stream.concat(Stream.of(s.getSeedUrl(), s.createdDate.toString()),
                        createStreamOfHitsNumbers(terms, s)).toArray(String[]::new))
                .forEach(data -> csvWriter.writeNext(data, false));
    }

    private Stream<String> createStreamOfHitsNumbers(Set<String> terms, TermsStatistics s) {
        return terms.stream()
                .map(t -> convertTermToHitsPairListToHashMap(s.getTermToHitsPairs())
                        .getOrDefault(t, 0).toString());
    }

    /**
     * Required to avoid linear search of number of hits for each term.
     */
    private HashMap<String, Integer> convertTermToHitsPairListToHashMap(List<TermToHitsPair> termToHitsPairs) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        termToHitsPairs.forEach(termToHitsPair -> hashMap.put(termToHitsPair.getTerm(),
                termToHitsPair.getNumberOfHits()));
        return hashMap;
    }
}

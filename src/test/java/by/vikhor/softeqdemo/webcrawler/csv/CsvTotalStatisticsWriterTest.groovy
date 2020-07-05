package by.vikhor.softeqdemo.webcrawler.csv

import by.vikhor.softeqdemo.webcrawler.entity.TermsStatistics
import spock.lang.Specification

class CsvTotalStatisticsWriterTest extends Specification {

    private CsvTotalStatisticsWriter csvTotalStatisticsWriter

    def "test writeTotalStatistics"() {
        given:
            csvTotalStatisticsWriter = new CsvTotalStatisticsWriter()
            def stringWriter = new StringWriter()
            def terms = Set.of("Java", "Junior")
            def stat1 = new TermsStatistics()
            stat1.seedUrl = "url1"
            stat1.statistics = new HashMap<>()
            stat1.statistics.put("Java", 5)
            stat1.statistics.put("Junior", 6)
            def stat2 = new TermsStatistics()
            stat2.seedUrl = "url2"
            stat2.statistics = new HashMap<>()
            stat2.statistics.put("Java", 6)
            stat2.statistics.put("Junior", 5)
            stat2.statistics.put("Other", 0)
        when:
            csvTotalStatisticsWriter.writeTotalStatistics(List.of(stat1, stat2), terms, stringWriter)
            stringWriter.flush()
        then:
            stringWriter.toString() == "\"SeedUrl\";\"Java\";\"Junior\"\n" +
                                       "url1;5;6\n" +
                                       "url2;6;5\n"
    }
}

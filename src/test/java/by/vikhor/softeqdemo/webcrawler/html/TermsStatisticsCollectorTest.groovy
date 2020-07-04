package by.vikhor.softeqdemo.webcrawler.html

import by.vikhor.softeqdemo.webcrawler.TestConstants
import spock.lang.Specification

class TermsStatisticsCollectorTest extends Specification {

    private TermsStatisticsCollector termsStatisticsCollector

    def "should accurately collect terms statistics"() {
        given:
            termsStatisticsCollector = new TermsStatisticsCollector()
        when:
            def statistics = termsStatisticsCollector
                .collectTermsStatistics(TestConstants.HTML_DOC_WITH_A_LINK, Set.of("html", "link"))
        then:
            statistics.get("link") == 3 && statistics.get("html") == 2
    }
}

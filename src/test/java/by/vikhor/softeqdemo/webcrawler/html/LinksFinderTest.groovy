package by.vikhor.softeqdemo.webcrawler.html

import spock.lang.Specification
import by.vikhor.softeqdemo.webcrawler.TestConstants

class LinksFinderTest extends Specification {

    private LinksFinder linksFinder

    def "Should find a link in html document" () {
        given:
            linksFinder = new LinksFinder()
        when: "Html doc with only one link is provided"
            def links = linksFinder.findAllLinks(TestConstants.HTML_DOC_WITH_A_LINK)
        then:
            links.contains(TestConstants.LINK) && links.size() == 1

    }

    def "Should return an emply list" () {
        given:
            linksFinder = new LinksFinder()
        when: "Html doc with no links is provided"
            def links = linksFinder.findAllLinks(TestConstants.HTML_DOCK_WITHOUT_LINK)
        then:
            links.empty
    }
}

package frontlinesms.legal.cases

import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.Case
import frontlinesms.legal.functionaltests.pages.cases.SearchCasePage
import frontlinesms.legal.functionaltests.pages.HomePage
import frontlinesms.legal.functionaltests.pages.contact.SearchLegalContactPage

class SearchCaseSpec extends FrontlinesmsLegalGebSpec {

    def "should have all the cases in the search results after a previous search has been done and the case search page is reopened"() {
        given:
        new Case(caseId: "123", description: "test1").save(flush: true)
        new Case(caseId: "321", description: "test2").save(flush: true)
        to SearchCasePage
        and:
        id.value("12")
        sleep(500)

        when:
        goToContactSearchPage.click()
        to SearchLegalContactPage
        goToSearchPage.click()
        to SearchCasePage

        then:
        assert id.value() == ""
        caseLinkNotVisible().size()==0
    }

    def "should display only the filtered search on pressing RETURN"() {
        given:
        new Case(caseId: "123", description: "test1").save(flush: true)
        new Case(caseId: "321", description: "test2").save(flush: true)
        to SearchCasePage

        when:
        id.value("1234\r")
        sleep(500)

        then:
        caseLinkNotVisible().size()==2
    }

    def "should display case titles for each case listed in the search"() {
        given:
        new Case(caseId: "123", description: "test1", caseTitle:"Case Title 1").save(flush: true)
        new Case(caseId: "321", description: "test2", caseTitle:"Case Title 2").save(flush: true)

        when:
        at SearchCasePage

        then:
        searchResults.collect { it -> it.caseTitle }.size() == 2
    }
}

package frontlinesms.legal.cases

import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.Case
import frontlinesms.legal.functionaltests.pages.cases.SearchCasePage
import frontlinesms.legal.functionaltests.pages.HomePage
import frontlinesms.legal.functionaltests.pages.contact.SearchLegalContactPage

class SearchCaseSpec extends FrontlinesmsLegalGebSpec {

    def setup() {
        new Case(caseId: "123", description: "test1", caseTitle: "Case Title 1").save(flush: true)
        new Case(caseId: "321", description: "test2", caseTitle: "Case Title 2").save(flush: true)
    }

    def "should have all the cases in the search results after a previous search has been done and the case search page is reopened"() {
        given:
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
        caseLinkNotVisible().size() == 0
    }

    def "should display only the filtered search on pressing RETURN"() {
        given:
        to SearchCasePage

        when:
        id.value("1234\r")
        sleep(500)

        then:
        caseLinkNotVisible().size() == 2
    }

    def "should display case titles for each case listed in the search"() {
        when:
        to SearchCasePage

        then:
        searchResults.collect { it -> it.caseTitle }.size() == 2
    }

    def 'should not delete when NO is clicked on delete confirmation dialog'() {
        given:
        to SearchCasePage

        when:
        searchResults[0].deleteButton.click()

        and:
        deleteNo.click()

        then:
        searchResults().size() == 2
    }

    def 'should delete case from database when YES is clicked'() {
        given:
        to SearchCasePage

        when:
        searchResults[0].deleteButton.click();
        deleteYes.click();

        then:
        searchResults().size() == 1

    }

    def 'should stay on search page when YES is clicked in delete confirmation dialog'() {
        given:
        to SearchCasePage

        when:
        searchResults[0].deleteButton.click();
        deleteYes.click();

        then:
        assert at(SearchCasePage)

    }
}

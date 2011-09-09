package frontlinesms.legal.cases

import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.Case
import frontlinesms.legal.functionaltests.pages.cases.SearchCasePage
import javax.naming.directory.SearchResult

class DeleteCaseFromSearchCasePageSpec extends FrontlinesmsLegalGebSpec {

    def setup() {
        new Case(caseId: "123", description: "test").save(flush: true)
        new Case(caseId: "321", description: "test2").save(flush: true)
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

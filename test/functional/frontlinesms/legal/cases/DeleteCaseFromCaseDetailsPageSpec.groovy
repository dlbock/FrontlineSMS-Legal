package frontlinesms.legal.cases

import frontlinesms.legal.Case
import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.cases.ShowCasePage
import frontlinesms.legal.functionaltests.pages.cases.SearchCasePage

class DeleteCaseFromCaseDetailsPageSpec extends FrontlinesmsLegalGebSpec {

    def setup() {
        new Case(caseId: "123", description: "test").save(flush: true)
        new Case(caseId: "321", description: "test2").save(flush: true)
    }
    
    def "should have a delete button"() {
        when:
        to ShowCasePage, "123"

        then:
        assert deleteButton.size()==1
    }

    def 'should not delete when NO is clicked on delete confirmation dialog'() {
        given:
        to ShowCasePage, "123"

        when:
        deleteButton.click()

        and:
        deleteConfirmationDialog.noButton.click();

        then:
        at(ShowCasePage)
    }

    def 'should stay on search page when YES is clicked in delete confirmation dialog'() {
        given:
        to ShowCasePage, "123"

        when:
        deleteButton.click();

        deleteConfirmationDialog.confirm();

        then:
        assert at(SearchCasePage)
    }
}


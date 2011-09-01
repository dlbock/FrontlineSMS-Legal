package frontlinesms.legal.contacts

import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.HomePage
import frontlinesms.legal.functionaltests.pages.contact.CreateLegalContactPage
import frontlinesms.legal.functionaltests.pages.contact.ShowLegalContactPage
import frontlinesms.legal.Case
import frontlinesms.legal.LegalContact

class CreateLegalContactSpec extends FrontlinesmsLegalGebSpec {

    def setup(){

    }

    def "should save contact and redirect to show page when user chooses to save contact without name"() {
        given:
        to HomePage
        createNewContact.click()

        when:
        at CreateLegalContactPage
        name = ""
        primaryMobile = "8675309"

        and:
        save.click()

        and:
        saveWithoutNameYes.click()

        then:
        assert at(ShowLegalContactPage)
        primaryMobile == "8675309"
    }

    def 'should go to home page when yes is clicked on cancel creation of contact when some fields are filled in'() {
        given:
        to CreateLegalContactPage

        when:
        name = "Bob"
        cancel.click()

        and:
        cancelYes.click()

        then:
        assert at(HomePage)
    }

    def "should see link case button on the create legal contact page"() {
        when:
        to CreateLegalContactPage

        then:
        linkCaseButton.size() == 1
    }

    def "should show link case search window with search box and list of cases when link case button is clicked"() {
        given:
        new Case(caseId: "123", description: "test").save(flush: true)
        new Case(caseId: "321", description: "test2").save(flush: true)

        when:
        to CreateLegalContactPage
        linkCaseButton.click()

        then:
        linkCaseDialog.present
        casesToLink.collect { it -> it.linkCaseButton }.size() == 2
        caseIdSearch.present
        linkCaseCancelButton.present
    }

}

package frontlinesms.legal.cases

import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.HomePage
import frontlinesms.legal.functionaltests.pages.cases.NewCasePage
import frontlinesms.legal.functionaltests.pages.cases.ShowCasePage
import frontlinesms.legal.LegalContact
import org.junit.runner.Description
import frontlinesms.legal.Case

class CreateCaseSpec extends FrontlinesmsLegalGebSpec {

    def "should be able to create case with title, id, description"() {
        given:
        to NewCasePage

        when:
        caseTitle = "Test Title"
        caseId = "123"
        description = "whatever"

        and:
        save.click()

        then:
        assert at(ShowCasePage)
        caseTitle == "Test Title"
    }

    def "should display a error message when the user tries to save the case without case id"() {
        given:
        to NewCasePage

        when:
        caseTitle = "Test Title"
        caseId = ""

        and:
        save.click()

        then:
        errorMessage == "Case ID is required"
    }

    def 'should remain on Create case page when NO is clicked while saving case without case title'() {
        given:
        to NewCasePage

        when:
        caseId = "123"
        save.click()

        and:
        saveWithoutCaseTitleNo.click()

        then:
        assert at(NewCasePage)
    }

    def 'should remain on Create case page when no is clicked on cancel confirm dialog'() {
        given:
        to NewCasePage

        when:
        caseId = "123"
        cancel.click()

        and:
        cancelNo.click()

        then:
        assert at(NewCasePage)
    }

    def 'should go to home page when yes is clicked on cancel confirm dialog'() {
        given:
        to NewCasePage

        when:
        caseId = "123"
        cancel.click()

        and:
        cancelYes.click()

        then:
        assert at(HomePage)
    }

    def "should display error message when the user tries to save case with the existing case id"() {
        setup:
        new Case(caseId: "1112", description: "ertyui").save(flush: true)

        when:
        to NewCasePage
        caseTitle = "Test Title"
        caseId = "1112"
        save.click()

        then:
        errorMessage == "Case ID already exists. Please enter a unique Case ID."
    }

    def "should display all the contacts filtered by the value in the search input of the search contact dialog"() {
        setup:
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "dev", primaryMobile: "55555").save(flush: true)

        when:
        to NewCasePage
        and:
        linkContact.click()
        and:
        contactNameSearch.value("fab")
        sleep(500)

        then:
        contactLinkNotVisible().size() == 1
    }

    def "should continue showing the search contact dialog box after pressing RETURN when searching for a contact"() {
        setup:
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "dev", primaryMobile: "55555").save(flush: true)

        when:
        to NewCasePage
        and:
        linkContact.click()
        and:
        contactNameSearch.value("\r")

        then:
        linkContactDialog.displayed == true
    }

    def "should clear the search input when the dialog box is open, closed, reopened"() {
        setup:
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "dev", primaryMobile: "55555").save(flush: true)

        when:
        to NewCasePage
        and:
        linkContact.click()
        and:
        contactNameSearch.value("fab")
        sleep(500)
        and:
        linkContactDialogCancelButton.click()
        and:
        linkContact.click()

        then:
        contactNameSearch.value() == ""
    }

    def "should display all the contacts when the dialog box is reopened after a previous filter"() {
        setup:
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "dev", primaryMobile: "55555").save(flush: true)

        when:
        to NewCasePage
        and:
        linkContact.click()
        and:
        contactNameSearch.value("fab")
        sleep(500)
        and:
        linkContactDialogCancelButton.click()
        and:
        linkContact.click()

        then:
        contactLinkNotVisible().size() == 0
    }

    def "should not link contact to the case when cancel is clicked on the relationship dialog"() {
        setup:
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "dev", primaryMobile: "55555").save(flush: true)

        when:
        to NewCasePage
        and:
        linkContact.click()
        contactListInPopUp[0].click()
        relationshipCancelButton.click()

        then:
        linkContactDialog.present
        noLinkedContacts
    }
}
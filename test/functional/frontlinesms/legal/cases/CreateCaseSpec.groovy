package frontlinesms.legal.cases

import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.HomePage
import frontlinesms.legal.functionaltests.pages.cases.NewCasePage
import frontlinesms.legal.functionaltests.pages.cases.ShowCasePage
import frontlinesms.legal.LegalContact

class CreateCaseSpec extends FrontlinesmsLegalGebSpec {

    def "should be able to navigate to the create case page from the menu"() {
        given:
        to HomePage

        when:
        createNewCase.click()

        then:
        assert at(NewCasePage)
    }

    def "should be able to create case with id, description"() {
        given:
        to NewCasePage

        when:
        caseId = "123"
        description = "whatever"

        and:
        save.click()

        then:
        assert at(ShowCasePage)
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

    def "should show the search contact dialog box after pressing LINK CONTACT button"() {
        when:
        to NewCasePage
        and:
        clickLinkContact.click()

        then:
        linkContactDialog.displayed == true
    }

    def "should display all the contacts filtered by the value in the search input of the search contact dialog"() {
        setup:
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "dev", primaryMobile: "55555").save(flush: true)

        when:
        to NewCasePage
        and:
        clickLinkContact.click()
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
        clickLinkContact.click()
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
        clickLinkContact.click()
        and:
        contactNameSearch.value("fab")
        sleep(500)
        and:
        clickDialogCancelButton.click()
        and:
        clickLinkContact.click()

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
        clickLinkContact.click()
        and:
        contactNameSearch.value("fab")
        sleep(500)
        and:
        clickDialogCancelButton.click()
        and:
        clickLinkContact.click()

        then:
        contactLinkNotVisible().size() == 0
    }
}
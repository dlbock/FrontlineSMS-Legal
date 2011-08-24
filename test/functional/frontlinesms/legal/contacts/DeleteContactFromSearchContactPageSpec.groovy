package frontlinesms.legal.contacts

import frontlinesms.legal.LegalContact
import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.contact.SearchLegalContactPage

class DeleteContactFromSearchContactPageSpec extends FrontlinesmsLegalGebSpec {
    def createContact() {
        def contact1 = new LegalContact(name: 'Me', primaryMobile: '98765')
        contact1.save(flush: true)
        contact1.id
    }


    def "should have as many delete buttons as contacts"() {
        setup:
        createContact()

        when:
        to SearchLegalContactPage

        then:
        assert searchResults.collect { it -> it.deleteButton }.size() == 1
    }

    def 'should not delete when NO is clicked on delete confirmation dialog'() {
        setup:
        createContact()
        to SearchLegalContactPage

        when:
        searchResults[0].deleteButton.click()

        and:
        deleteNo.click()

        then:
        searchResults().size() == 1
    }

    def 'should delete contact from database when YES is clicked'() {
        setup:
        createContact()
        to SearchLegalContactPage

        when:
        searchResults[0].deleteButton.click();
        deleteYes.click();

        then:
        searchResults().size() == 0

    }

    def 'should stay on search page when YES is clicked in delete confirmation dialog'() {
        setup:
        createContact()
        to SearchLegalContactPage

        when:
        searchResults[0].deleteButton.click();
        deleteYes.click();

        then:
        assert at(SearchLegalContactPage)

    }
}

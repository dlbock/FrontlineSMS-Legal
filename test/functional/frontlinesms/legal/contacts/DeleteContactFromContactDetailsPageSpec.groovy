package frontlinesms.legal.contacts

import frontlinesms.legal.LegalContact
import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.contact.ShowLegalContactPage
import frontlinesms.legal.functionaltests.pages.contact.SearchLegalContactPage

class DeleteContactFromContactDetailsPageSpec extends FrontlinesmsLegalGebSpec {

    def createContact() {
        def contact1 = new LegalContact(name: 'Me', primaryMobile: '98765')
        contact1.save(flush: true)
        contact1.id
    }

    def "should have a delete button"() {
        setup:
        def contactId = createContact()

        when:
        to ShowLegalContactPage, contactId

        then:
        assert deleteButton.size() == 1
    }

    def 'should not delete when NO is clicked on delete confirmation dialog'() {
        setup:
        def contactId = createContact()
        to ShowLegalContactPage, contactId

        when:
        deleteButton.click()

        and:
        deleteNo.click()

        then:
        at(ShowLegalContactPage)
    }

    def 'should go to search page when YES is clicked in delete confirmation dialog'() {
            setup:
            def contactId = createContact()
            to ShowLegalContactPage, contactId

            when:
            deleteButton.click();
            deleteYes.click();
            waitFor { !deleteDialog.isVisible() }

            then:
            assert at(SearchLegalContactPage)

        }
}


package frontlinesms.legal.events

import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.events.NewEventPage
import frontlinesms.legal.functionaltests.pages.schedule.SchedulePage
import frontlinesms.legal.LegalContact

class LinkContactToExistingEventSpec extends FrontlinesmsLegalGebSpec {
    def 'should display all contacts in the Link Contact to an Event popup'() {
        given:
        createEvent("Test Event")
        new LegalContact(name: 'fa', primaryMobile: '1234567').save(flush: true)
        new LegalContact(name: 'faooio', primaryMobile: '12309004567').save(flush: true)
        to SchedulePage, "index"

        when:
        events()[0].click()
        linkContactToExistingEvent()

        then:
        assert existingContactList.collect { it -> it.contactName }.size() == 2
    }

    def 'should display a summary of all contacts in the Link Contact to an Event popup'() {
        given:
        createEvent("Test Event")
        new LegalContact(name: 'fa', primaryMobile: '1234567').save(flush: true)
        to SchedulePage, "index"

        when:
        events()[0].click()
        linkContactToExistingEvent()

        then:
        existingContactList[0].contactName == "fa"
        existingContactList[0].contactNumber == "1234567"
    }

    def 'should link contact to an existing event when the LINK CONTACT link (next to respective contact) is clicked on th Link Contact to an Event popup'() {
        given:
        createEvent("Test")
        new LegalContact(name: 'fa', primaryMobile: '1234567').save(flush: true)
        new LegalContact(name: 'fafd', primaryMobile: '1234567').save(flush: true)
        to SchedulePage, "index"

        when:
        events()[0].click()
        linkContactToExistingEvent()

        and:
        linkContactFromPopup()

        then:
        linkedContactsInEventDetailsPopup.collect { it -> it.contactName }.size() == 1
    }

    def 'should link the correct contact to an exisitng event when the LINK CONTACT link next to it is clicked'() {
        given:
        createEvent("Test")
        new LegalContact(name: 'fa', primaryMobile: '1234567').save(flush: true)
        new LegalContact(name: 'fafd', primaryMobile: '1234567').save(flush: true)
        to SchedulePage, "index"

        when:
        events()[0].click()
        linkContactToExistingEvent()

        and:
        linkContactFromPopup()

        then:
        linkedContactsInEventDetailsPopup.collect { it -> it.contactName }[0] == 'fa'
        linkedContactsInEventDetailsPopup.collect { it -> it.contactNumber }[0] == '1234567'
    }

    def 'should not link any contacts when cancel button is clicked'(){
        given:
        createEvent("Test")
        new LegalContact(name: 'fa', primaryMobile: '1234567').save(flush: true)
        new LegalContact(name: 'fafd', primaryMobile: '1234567').save(flush: true)
        to SchedulePage, "index"

        when:
        events()[0].click()
        linkContactToExistingEvent()

        and:
        CancelButtonIsClicked()

        then:
        linkedContactsInEventDetailsPopup.collect { it -> it.contactName }.size() == 0
    }

    def "should display all the contacts filtered by the value in the search input of the search contact dialog"() {
        given:
        createEvent("Test")
        new LegalContact(name: "neetu", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "rupa", primaryMobile: "55555").save(flush: true)
        to SchedulePage, "index"

        when:
        events()[0].click()
        linkContactToExistingEvent()

        and:
        contactNameSearch.value("ne")
        sleep(500)

        then:
        contactLinkNotVisible().size() == 1
    }

    def "should clear the search input when the dialog box is open, closed, reopened"() {
        given:
        createEvent("Test")
        new LegalContact(name: "neetu", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "rupa", primaryMobile: "55555").save(flush: true)
        to SchedulePage, "index"

        when:
        events()[0].click()
        linkContactToExistingEvent()

        and:
        contactNameSearch.value("ne")
        sleep(500)
        and:
        CancelButtonIsClicked()
        and:
        linkContactToExistingEvent()

        then:
        contactNameSearch.value() == ""
    }

    def "should display all the contacts when the dialog box is reopened after a previous filter"() {
        given:
        createEvent("Test")
        new LegalContact(name: "neetu", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "rupa", primaryMobile: "55555").save(flush: true)
        to SchedulePage, "index"

        when:
        events()[0].click()
        linkContactToExistingEvent()

        and:
        contactNameSearch.value("ne")
        sleep(500)
        and:
        CancelButtonIsClicked()
        and:
        linkContactToExistingEvent()

        then:
        existingContactList.collect { it -> it.contactName }.size() == 2
    }

    def createEvent(title) {
        to NewEventPage
        eventTitle = title
        dateFieldSelected = new Date().format("MMMM d, yyyy")
        startTimeField = "08:09AM"
        endTimeField = "08:56PM"
        save.click()
    }
}
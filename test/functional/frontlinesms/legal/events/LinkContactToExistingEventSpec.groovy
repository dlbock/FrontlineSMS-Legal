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
        existingContactList.collect { it -> it.contactName }.size() == 2
        and:
        existingContactList[0].contactName == "fa"
        existingContactList[0].contactNumber == "1234567"
        existingContactList[1].contactName == "faooio"
        existingContactList[1].contactNumber == "12309004567"

    }

    def 'should link the correct contact to an existing event when the LINK CONTACT link next to it is clicked'() {
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

        and:
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

    def 'should be able to link a contact to multiple events'(){
        given:
        createEvent("Test1")
        createEvent("Test2")
        new LegalContact(name: 'fa', primaryMobile: '1234567').save(flush: true)
        to SchedulePage, "index"

        when:
        events()[0].click()
        linkContactToExistingEvent()
        linkContactFromPopup()
        to SchedulePage, "index"

        and:
        events()[1].click()
        linkContactToExistingEvent()
        linkContactFromPopup()

        then:
        linkedContactsInEventDetailsPopup.collect { it -> it.contactName }[0] == 'fa'
    }

    def "should clear the search input when the contacts search dialog is opened, closed and reopened"() {
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

    def "should display all the contacts filtered by the value in the search input of the search contact dialog"() {
        given:
        createEvent("Test")
        new LegalContact(name: "neetu", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "neil", primaryMobile: "11111").save(flush: true)
        new LegalContact(name: "rupa", primaryMobile: "55555").save(flush: true)
        to SchedulePage, "index"

        when:
        events()[0].click()
        linkContactToExistingEvent()

        and:
        contactNameSearch.value("ne")
        sleep(500)

        then:
        contactsNotInSearchResults().size() == 1
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

    def "should not be able to link already linked contact"() {
        given:
        createEventWithContact("Test")
        to SchedulePage, "index"

        when:
        events()[0].click()
        linkContactToExistingEvent()
        linkContactFromPopup()

        then:
        linkedContactsInEventDetailsPopup.collect { it -> it.contactName }.size() == 1

        and:
        linkedContactsInEventDetailsPopup.collect { it -> it.contactName }[0] == "neetu"
    }

    def "should continue showing the search contact dialog box after pressing RETURN when searching for a contact"() {
        setup:
        createEvent("Test")
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "dev", primaryMobile: "55555").save(flush: true)

        when:
        to SchedulePage, "index"
        events()[0].click()
        linkContactToExistingEvent()
        contactNameSearch.value("fab\r")

        then:
        linkedContactsInEventDetailsPopup.collect { it -> it.contactName }.size() == 0

        and:
        linkContactSearchDialog.displayed == true
    }

    def "should link contact to event on hitting the update button"() {
        given:
        createEvent("Test")
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        to SchedulePage, "index"

        when:
        events()[0].click()
        linkContactToExistingEvent()
        linkContactFromPopup()
        updateEvent.click()

        then:
        events()[0].click()
        linkedContactsInEventDetailsPopup.collect { it -> it.contactName }.size() == 1
    }

    private def createEvent(title) {
        to NewEventPage
        eventTitle = title
        dateFieldSelected = new Date().format("MMMM d, yyyy")
        startTimeField = "08:09AM"
        endTimeField = "08:56PM"
        save.click()
    }

    private def createEventWithContact(title) {
        new LegalContact(name: "neetu", primaryMobile: "22222").save(flush: true)
        to NewEventPage
        eventTitle = title
        dateFieldSelected = new Date().format("MMMM d, yyyy")
        startTimeField = "08:09AM"
        endTimeField = "08:56PM"
        clickLinkContact.click()
        contactNameSearch.value("ne")
        sleep(500)
        contactsToLink[0].click()
        save.click()
    }

}
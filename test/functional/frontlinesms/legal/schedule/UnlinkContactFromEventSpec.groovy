package frontlinesms.legal.schedule

import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.contact.CreateLegalContactPage
import frontlinesms.legal.functionaltests.pages.events.NewEventPage
import frontlinesms.legal.functionaltests.pages.schedule.SchedulePage

class UnlinkContactFromEventSpec extends FrontlinesmsLegalGebSpec {

    def 'should display unlink link next to each contact on event details pop-up'() {

        given:
        createContact("76575658", "Bob")
        createEventWithLink("test event", "Bob")
        to SchedulePage, "index"

        when:
        at SchedulePage
        events.find{ it.text() == "test event" }.click()

        then:
        eventDialog.contactsLinkedToEvent.collect { it -> it.unlinkContact }.size() == 1
    }

    def 'should remove pre-existing contact when unlink link next to it is clicked on event details pop-up'() {

        given:
        createContact("76575658", "Bob")
        createEventWithLink("test event", "Bob")
        to SchedulePage, "index"

        when:
        at SchedulePage
        events.find{ it.text() == "test event" }.click()
        eventDialog.contactsLinkedToEvent[0].unlinkContact.click()

        then:
        eventDialog.contactsLinkedToEvent.size() == 0
    }

    def 'should remove newly created contact when unlink link next to it is clicked on event details pop-up'() {

        given:
        createContact("76575658", "Bob")
        createEvent("test event")
        to SchedulePage, "index"

        when:
        at SchedulePage
        events.find{ it.text() == "test event" }.click()
        linkContactToExistingEvent
        linkContactFromPopup
        updateEvent.click()

        and:
        events.find{ it.text() == "test event" }.click()
        eventDialog.contactsLinkedToEvent[0].unlinkContact.click()

        then:
        eventDialog.contactsLinkedToEvent.size() == 0
    }

    def 'should enable update button when unlink link next to a linked contact is clicked on event details pop-up'() {

        given:
        createContact("76575658", "Bob")
        createEventWithLink("test event", "Bob")
        to SchedulePage, "index"

        when:
        at SchedulePage
        events.find{ it.text() == "test event" }.click()
        eventDialog.contactsLinkedToEvent[0].unlinkContact.click()

        then:
        updateEvent
    }

    private def createContact(number, name) {
        to CreateLegalContactPage
        this.name = name
        primaryMobile = number
        notes = "Blah"
        save.click()
    }

    private def createEventWithLink(title, String... names) {
        to NewEventPage
        eventTitle = title
        setDate()
        startTimeField = "08:09AM"
        endTimeField = "08:56PM"
        for (name in names) {
            linkContactButton.click()
            contactsToLink.find{ it.name == name }.click()
        }
        save.click()
    }

    private def createEvent(title) {
        to NewEventPage
        eventTitle = title
        setDate()
        startTimeField = "08:09AM"
        endTimeField = "08:56PM"
        save.click()
    }
}

package frontlinesms.legal.schedule

import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.contact.CreateLegalContactPage
import frontlinesms.legal.functionaltests.pages.events.NewEventPage
import frontlinesms.legal.functionaltests.pages.schedule.SchedulePage

class UnlinkContactFromEventSpec extends FrontlinesmsLegalGebSpec {

    def 'should remove pre-linked contact when unlink is clicked on the event details pop-up'() {
        given:
        createContact("76575658", "Bob")
        createEventWithLink("test event", "Bob")
        to SchedulePage, "index"

        when:
        selectEvent "test event"
        eventDialog.unlinkFirstContactLinkedToEvent()

        then:
        !eventDialog.contactsLinkedToEvent.any { it.name == "Bob" }
    }

    def 'should remove newly linked contact when unlink is clicked on the event details pop-up'() {
        given:
        createContact("76575658", "Bob")
        createEvent("test event")
        to SchedulePage, "index"

        when:
        selectEvent("test event")
        eventDialog.linkContact()
        eventDialog.linkContactDialog.link("Bob")

        and:
        eventDialog.unlinkFirstContactLinkedToEvent()

        then:
        !eventDialog.contactsLinkedToEvent.any { it.name == "Bob" }
    }

    def 'should unlink contact from an event when the UPDATE button is clicked'() {
        given:
        createContact("76575658", "Bob")
        createEventWithLink("test event", "Bob")
        to SchedulePage, "index"

        when:
        selectEvent "test event"
        eventDialog.unlinkFirstContactLinkedToEvent()
        eventDialog.updateEvent()

        and:
        selectEvent "test event"

        then:
        !eventDialog.contactsLinkedToEvent.any { it.name == "Bob" }
    }

    def 'should not unlink contact when X(close) is clicked on the event-details pop up'(){
        given:
        createContact("76575658", "Bob")
        createEventWithLink("test event", "Bob")
        to SchedulePage, "index"

        when:
        selectEvent "test event"
        eventDialog.unlinkFirstContactLinkedToEvent()
        eventDialog.close
        eventDialog.confirmYesOnCloseWithoutUpdating

        and:
        selectEvent "test event"

        then:
        eventDialog.contactsLinkedToEvent.any { it.name == "Bob" }
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
        date.setDate(16)
        startTimeField = "08:09AM"
        endTimeField = "08:56PM"
        for (name in names) {
            linkContact()
            linkContactDialog.link(name)
        }
        save.click()
    }

    private def createEvent(title) {
        to NewEventPage
        eventTitle = title
        date.setDate(16)
        startTimeField = "08:09AM"
        endTimeField = "08:56PM"
        save.click()
    }
}

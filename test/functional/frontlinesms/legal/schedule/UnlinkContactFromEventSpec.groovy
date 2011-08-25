package frontlinesms.legal.schedule

import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.contact.CreateLegalContactPage
import frontlinesms.legal.functionaltests.pages.events.NewEventPage
import frontlinesms.legal.functionaltests.pages.schedule.SchedulePage

class UnlinkContactFromEventSpec extends FrontlinesmsLegalGebSpec {

    def 'should display unlink button next to each contact on event details pop-up'() {

        given:
        createContact("76575658", "Bob")
        createEventWithLink("test event", "Bob")
        to SchedulePage, "index"

        when:
        at SchedulePage
        testEvent.click()

        then:
        eventDialog.contactsLinkedToEvent.collect { it -> it.unlinkContact }.size() == 1
    }

    def 'should not unlink contact when NO is clicked on unlink contact confirmation dialog'() {

        given:
        createContact("7657558", "Sally")
        createEventWithLink("test event", "Sally")
        to SchedulePage, "index"

        when:
        at SchedulePage
        testEvent.click()
        eventDialog.contactsLinkedToEvent[0].unlinkContact.click()

        and:
        unlinkConfirmationDialog.noButton.click()

        then:
        eventDialog.contactsLinkedToEvent.size() == 1
    }

    def 'should unlink contact when YES is clicked on unlink contact confirmation dialog'() {

        given:
        createContact("7657558", "Fred")
        createContact("7657555", "Mary")
        createEventWithLink("test event", "Fred", "Mary")
        to SchedulePage, "index"

        when:
        at SchedulePage
        testEvent.click()
        eventDialog.contactsLinkedToEvent[0].unlinkContact.click()

        and:
        unlinkConfirmationDialog.confirm()

        then:
        eventDialog.contactsLinkedToEvent.size() == 1
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
        dateFieldSelected = new Date().format("MMMM d, yyyy")
        startTimeField = "08:09AM"
        endTimeField = "08:56PM"
        for (name in names) {
            linkContactButton.click()
            contactsToLink.find{ it.name == name }.click()
        }
        save.click()
    }
}

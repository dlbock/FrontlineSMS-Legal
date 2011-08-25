package frontlinesms.legal.schedule

import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.contact.CreateLegalContactPage
import frontlinesms.legal.functionaltests.pages.events.NewEventPage
import frontlinesms.legal.functionaltests.pages.schedule.SchedulePage

class UnlinkContactFromEventSpec extends FrontlinesmsLegalGebSpec {

    def 'should display unlink button next to each contact on events detail pop-up'(){

       given:
        createContact("76575658")
        createEventWithLink("test event")
        to SchedulePage, "index"

        when:
        at SchedulePage
        testEvent.click()

        then:
        eventDialog.contactsLinkedToEvent.collect { it -> it.unlinkContact }.size() == 1
    }

    def 'should not unlink contact when NO is clicked on unlink contact confirmation dialog'() {

        given:
        createContact("7657558")

        createEventWithLink("test event")
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

    private def createContact(number) {
        to CreateLegalContactPage
        name = "Fred"
        primaryMobile = number
        notes = "Blah"
        save.click()
    }

    private def createEventWithLink(title) {
        to NewEventPage
        eventTitle = title
        dateFieldSelected = new Date().format("MMMM d, yyyy")
        startTimeField = "08:09AM"
        endTimeField = "08:56PM"
        linkContactButton.click()
        contactsToLink.first().click()
        save.click()
    }
}

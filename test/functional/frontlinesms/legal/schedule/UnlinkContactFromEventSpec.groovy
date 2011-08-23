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
        contactsLinkedToEvent.collect { it -> it.unlinkLink }.size() == 1
    }

    def 'should not unlink contact when NO is clicked on unlink contact confirmation dialog'() {
        given:
        createContact("76575658")
        createEventWithLink("test event")
        to SchedulePage, "index"

        when:
        at SchedulePage
        testEvent.click()
        contactsLinkedToEvent[0].unlinkLink.click()

        and:
        contactUnlinkNo.click()

        then:
        contactsLinkedToEvent.size() == 1
    }

    /*def 'should delete case from database when YES is clicked'() {
        given:
        to SearchCasePage

        when:
        searchResults[0].deleteButton.click();
        caseDeleteYes.click();

        then:
        searchResults().size() == 1
    }*/

    def createContact(number) {
        to CreateLegalContactPage
        name = "Fred"
        primaryMobile = number
        notes = "Blah"
        save.click()
    }

    def createEventWithLink(title) {
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

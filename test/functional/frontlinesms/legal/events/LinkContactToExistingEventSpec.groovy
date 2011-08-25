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

    def createEvent(title) {
        to NewEventPage
        eventTitle = title
        dateFieldSelected = new Date().format("MMMM d, yyyy")
        startTimeField = "08:09AM"
        endTimeField = "08:56PM"
        save.click()
    }
}
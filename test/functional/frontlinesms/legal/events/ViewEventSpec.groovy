package frontlinesms.legal.events

import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.events.NewEventPage
import frontlinesms.legal.functionaltests.pages.schedule.SchedulePage
import frontlinesms.legal.Case
import frontlinesms.legal.LegalContact

class ViewEventSpec extends FrontlinesmsLegalGebSpec {

    def "should display linked cases and contacts for event when event is clicked"() {
        given:
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        new Case(caseId: "123", description: "test", caseTitle: "Case 1").save(flush: true)
        createEventWithLinkedCaseAndLinkedContact("test event")
        to SchedulePage, "index"

        when:
        events()[0].click()
        sleep(500)

        then:
        existingCaseList.size() == 1
        existingContactList.size() == 1
    }


    def createEventWithLinkedCaseAndLinkedContact(title) {
        to NewEventPage
        eventTitle = title
        date.setDate(6)
        startTimeField = "08:09AM"
        endTimeField = "08:56PM"
        linkContact()
        linkContactDialog.link("fabio")
        linkCaseToEventButton.click()
        casesToLink[0].linkCase.click()
        save.click()
    }

}

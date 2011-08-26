package frontlinesms.legal.events

import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.schedule.SchedulePage
import frontlinesms.legal.functionaltests.pages.events.NewEventPage

class RescheduleEventSpec extends FrontlinesmsLegalGebSpec {

    def 'should show calendar when date text box is clicked'() {
        given:
        createEvent("Test Event 1", "08:09AM", "08:56PM")
        to SchedulePage, "index"

        when:
        events()[0].click()
        sleep(500)

        and:
        dateFieldSelected()

        then:
        datePicker.present
    }

    def createEvent(title, String startTime, String endTime) {
        to NewEventPage
        eventTitle = title
        dateFieldSelected = new Date().format("MMMM d, yyyy")
        startTimeField = startTime
        endTimeField = endTime
        save.click()
    }

}



package frontlinesms.legal.events

import frontlinesms.legal.Event
import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.events.NewEventPage
import frontlinesms.legal.functionaltests.pages.schedule.SchedulePage

class DeleteEventSpec extends FrontlinesmsLegalGebSpec {

    def setup() {
        Event.findAll()*.delete(flush: true)
    }

    def 'should delete event on clicking delete button on the pop-up dialog with event details'() {
        given:
        createEvent("Test Event 1", "08:09AM", "08:56PM")
        createEvent("Test Event 2", "09:09AM", "09:56PM")
        createEvent("Test Event 3", "10:09AM", "10:56PM")
        to SchedulePage, "index"

        when:
        events()[0].click()
        deleteEvent()

        then:
        events().size() == 2
    }

    def 'should not delete event on clicking No in confirm dialog'() {
        given:
        createEvent("Test Event", "08:09AM", "08:56PM")
        to SchedulePage, "index"

        when:
        def oldEventListSize = events().size();

        events()[0].click()
        $('#delete-event').click()
        $('#cancel-confirm-no').click()

        def newEventListSize = eventListSize()

        then:
        oldEventListSize == newEventListSize;
    }

    def 'should redirect to the event date on successful creation of the event'() {

        when:
        createEvent("Test1", "08:09AM", "08:56PM")
        then:
        at SchedulePage
        atDate == new Date().format("MMMM yyyy")

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

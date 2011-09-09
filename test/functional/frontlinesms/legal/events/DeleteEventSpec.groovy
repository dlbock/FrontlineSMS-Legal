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
        eventDialog.deleteEvent()

        then:
        events().size() == 2
    }

    def 'should not delete event on clicking No in confirm dialog'() {
        given:
        createEvent("Test Event", "08:09AM", "08:56PM")
        to SchedulePage, "index"

        and:
        def oldEventListSize = events().size();

        when:
        events()[0].click()
        eventDialog.deleteEventButton.click()
        deleteConfirmationDialog.noButton.click()

        then:
        events().size() == oldEventListSize;
    }

    def 'should redirect to the event date on successful creation of the event'() {
        when:
        createEvent("Test1", "08:09AM", "08:56PM")

        then:
        at SchedulePage
        calendarHeader == new Date().format("MMMM yyyy")
    }

    def 'should not display a confirmation dialog when deleting an edited event is confirmed'() {
        given:
        createEvent("Test Event", "08:09AM", "08:56PM")
        to SchedulePage, "index"

        when:
        events()[0].click()
        eventDialog.title << "foo"
        eventDialog.deleteEventButton.click()
        deleteConfirmationDialog.yesButton.click()

        then:
        cancelEditConfirmationDialog.displayed == false
    }

    def 'should not display a confirmation dialog when the deletion of an edited event is canceled'() {
        given:
        createEvent("Test Event", "08:09AM", "08:56PM")
        to SchedulePage, "index"

        when:
        events()[0].click()
        eventDialog.title << "foo"
        eventDialog.deleteEventButton.click()
        deleteConfirmationDialog.noButton.click()

        then:
        eventDialog.displayed == true

        and:
        cancelEditConfirmationDialog.displayed == false
    }

    def createEvent(title, String startTime, String endTime) {
        to NewEventPage
        eventTitle = title
        date.setDate(16)
        startTimeField = startTime
        endTimeField = endTime
        save.click()
    }
}

package frontlinesms.legal.events

import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.schedule.SchedulePage
import frontlinesms.legal.functionaltests.pages.events.NewEventPage
import frontlinesms.legal.Event

class RescheduleEventSpec extends FrontlinesmsLegalGebSpec {

    def setup() {
        given:
        createEvent("Test Event 1", "08:09AM", "08:56PM")
        to SchedulePage, "index"
    }

    def 'should show calendar when date text box is clicked'() {
        when:
        events()[0].click()

        and:
        dateFieldSelected()

        then:
        datePicker.present
    }

    def "should update event when the title has changed"() {
        when:
        events()[0].click()

        and:
        def newTitle = "Some new title"
        eventTitle = newTitle

        and:
        updateEvent.click()

        then:
        def updatedEvent = Event.findByEventTitle("Some new title")
        updatedEvent.eventTitle == newTitle
    }

    def "should show error message when title field is blank"() {
        when:
        events()[0].click()

        and:
        eventTitle = ""

        and:
        updateEvent.click()

        then:
        errorMessage == "An event must have a title, date and time. Please enter a title."
    }

    def "should show error message when date field is blank"() {
        when:
        events()[0].click()

        and:
        eventDate = ""

        and:
        updateEvent.click()

        then:
        errorMessage == "An event must have a title, date and time. Please enter a date."
    }

    def "should show error message when start time field is blank"() {
        when:
        events()[0].click()

        and:
        eventStartTime = ""

        and:
        updateEvent.click()

        then:
        errorMessage == "An event must have a title, date and time. Please enter a start time."
    }

    def "should show error message when end time field is blank"() {
        when:
        events()[0].click()

        and:
        eventEndTime = ""

        and:
        updateEvent.click()

        then:
        errorMessage == "An event must have a title, date and time. Please enter a end time."
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



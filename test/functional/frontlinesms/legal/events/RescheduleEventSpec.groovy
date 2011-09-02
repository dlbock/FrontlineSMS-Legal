package frontlinesms.legal.events

import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.schedule.SchedulePage
import frontlinesms.legal.functionaltests.pages.events.NewEventPage
import frontlinesms.legal.Event
import org.openqa.selenium.Keys

class RescheduleEventSpec extends FrontlinesmsLegalGebSpec {

    def setup() {
        given:
        createEvent("Test Event 1", "08:09AM", "08:56PM")
        to SchedulePage, "index"
    }

    def "should not enable the update button when text fields have no change"() {
        when:
        events()[0].click()

        then:
        updateEvent.@disabled == 'true'
    }

    def "should enable the update button when text fields have changes"() {
        when:
        events()[0].click()

        and:
        eventTitleField << "enable update button"

        then:
        updateEvent.@disabled == 'false'
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
        eventTitleField.value("")
        eventTitleField << "Some new title"

        and:
        updateEvent.click()

        then:
        def updatedEvent = Event.findByEventTitle("Some new title")
        updatedEvent.eventTitle == "Some new title"
    }

    def "should show error message when title field is blank"() {
        when:
        events()[0].click()

        and:
        eventTitleField.value("")
        eventTitleField << Keys.DELETE

        and:
        updateEvent.click()

        then:
        errorMessage == "An event must have a title, date and time. Please enter a title."
    }

    def "should show error message when date field is blank"() {
        when:
        events()[0].click()

        and:
        eventDateField.value("")
        eventDateField << Keys.DELETE

        and:
        updateEvent.click()

        then:
        errorMessage == "An event must have a title, date and time. Please enter a date."
    }

    def "should show error message when start time field is blank"() {
        when:
        events()[0].click()

        and:
        eventStartTimeField.value("")
        eventStartTimeField << Keys.DELETE

        and:
        updateEvent.click()

        then:
        errorMessage == "An event must have a title, date and time. Please enter a start time."
    }

    def "should show error message when end time field is blank"() {
        when:
        events()[0].click()

        and:
        eventEndTimeField.value("")
        eventEndTimeField << Keys.DELETE

        and:
        updateEvent.click()

        then:
        errorMessage == "An event must have a title, date and time. Please enter a end time."
    }

    def "should not show confirmation dialog when user close the event detail pop up and fields have no changes"() {
        when:
        events()[0].click()

        and:
        eventDialog << Keys.ESCAPE

        then:
        $('#confirmation-dialog').size() == 0
    }

    def "should show confirmation dialog when user close the event detail pop up and fields have changes"() {
        when:
        events()[0].click()

        and:
        eventTitleField << "hello"
        eventDialog << Keys.ESCAPE

        then:
        $('#confirmation-dialog').size() != 0
    }

    def "should return to event detail pop up when NO is clicked in confirmation dialog"() {
        when:
        events()[0].click()

        and:
        eventTitleField << "hello"
        eventDialog << Keys.ESCAPE

        and:
        noConfirmationButton.click()

        then:
        $('#confirmation-dialog').size() == 0
    }

    def "should close event detail pop up and confirmation dialog when YES is clicked in confirmation dialog"() {
        when:
        events()[0].click()

        and:
        eventTitleField << "hello"
        eventDialog << Keys.ESCAPE

        and:
        yesConfirmationButton.click()

        then:
        $('#confirmation-dialog').size() == 0
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



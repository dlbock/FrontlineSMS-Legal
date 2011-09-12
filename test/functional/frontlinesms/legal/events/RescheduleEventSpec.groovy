package frontlinesms.legal.events

import frontlinesms.legal.Event
import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.events.NewEventPage
import frontlinesms.legal.functionaltests.pages.schedule.SchedulePage
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
        eventDialog.updateEventButton.@disabled == 'true'
    }

    def "should enable the update button when text fields have changes"() {
        when:
        events()[0].click()

        and:
        eventDialog.title << "enable update button"

        then:
        eventDialog.updateEventButton.@disabled == 'false'
    }

    def 'should show calendar when date text box is clicked'() {
        when:
        events()[0].click()

        and:
        eventDialog.date.openDatePicker()

        then:
        eventDialog.date.datePicker.present
    }

    def "should update event when the title has changed"() {
        when:
        events()[0].click()

        and:
        eventDialog.title.value("")
        eventDialog.title << "Some new title"

        and:
        eventDialog.updateEvent()

        then:
        def updatedEvent = Event.findByEventTitle("Some new title")
        updatedEvent.eventTitle == "Some new title"
    }

    def "should show error message when title field is blank"() {
        when:
        events()[0].click()

        and:
        eventDialog.title.value("")
        eventDialog.title << Keys.DELETE

        and:
        eventDialog.updateEvent()

        then:
        eventDialog.errorMessage == "Please enter a title."
        and:
        eventDialog.updateEventButton.@disabled == 'true'
    }

    def "should show error message when date field is blank"() {
        when:
        events()[0].click()

        and:
        eventDialog.date.clear()

        and:
        eventDialog.updateEvent()

        then:
        eventDialog.errorMessage == "Please enter a date."
        and:
        eventDialog.updateEventButton.@disabled == 'true'
    }

    def "should show error message when start time field is blank"() {
        when:
        events()[0].click()

        and:
        eventDialog.startTime.value("")
        eventDialog.startTime << Keys.DELETE

        and:
        eventDialog.updateEvent()

        then:
        eventDialog.errorMessage == "Please enter a start time."
        and:
        eventDialog.updateEventButton.@disabled == 'true'
    }

    def "should show error message when end time field is blank"() {
        when:
        events()[0].click()

        and:
        eventDialog.endTime.value("")
        eventDialog.endTime << Keys.DELETE

        and:
        eventDialog.updateEvent()

        then:
        eventDialog.errorMessage == "Please enter a end time."
        and:
        eventDialog.updateEventButton.@disabled == 'true'
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
        eventDialog.title << "hello"
        eventDialog << Keys.ESCAPE

        then:
        $('#confirmation-dialog').size() != 0
    }

    def "should return to event detail pop up when NO is clicked in confirmation dialog"() {
        when:
        events()[0].click()

        and:
        eventDialog.title << "hello"
        eventDialog << Keys.ESCAPE

        and:
        cancelEditConfirmationDialog.noButton.click()

        then:
        cancelEditConfirmationDialog.size() == 0
    }

    def "should close event detail pop up and confirmation dialog when YES is clicked in confirmation dialog"() {
        when:
        events()[0].click()

        and:
        eventDialog.title << "hello"
        eventDialog << Keys.ESCAPE

        and:
        cancelEditConfirmationDialog.confirm()

        then:
        cancelEditConfirmationDialog.size() == 0
    }

    def "should enable only tab key on event date field"() {
        given:
        selectEvent("Test Event 1")

        and:
        def initialDate = eventDialog.date.value

        when:
        eventDialog.date.click()

        and:
        eventDialog.date << "a!dfj-*#"

        then:
        eventDialog.date.value() == initialDate
    }

    def "should not allow non-numeric keys on event start time field"() {
        when:
        selectEvent("Test Event 1")

        and:
        eventDialog.startTime.jquery.focus()

        and:
        eventDialog.startTime.click()

        and:
        eventDialog.startTime << "a!dfj-*#"

        then:
        eventDialog.startTime.value() == "08:09AM"
    }

    def "should not allow non-numeric keys on event end time field"() {
        when:
        selectEvent("Test Event 1")

        and:
        eventDialog.endTime.jquery.focus()

        and:
        eventDialog.endTime.click()

        and:
        eventDialog.endTime << "a!dfj-*#"

        then:
        eventDialog.endTime.value() == "08:56AM"
    }

    def "should not allow start time to be after end time"() {
        when:
        selectEvent("Test Event 1")

        and:
        eventDialog.startTime.value("11:30PM")

        and:
        eventDialog.endTime.value("12:30AM")

        and:
        eventDialog.updateEvent()

        then:
        eventDialog.errorMessage == "The end time cannot be earlier than the start time."
    }

    def "cancel button should close the event dialog window if no update is made"() {
        when:
        selectEvent("Test Event 1")

        and:
        eventDialog.cancel()

        then:
        eventDialog.displayed == false
        cancelEditConfirmationDialog.size() == 0
    }

    def "should display confirmation dialog when changes have been made to the fields and the cancel button is clicked"() {
        when:
        selectEvent("Test Event 1")

        and:
        eventDialog.title << "Update"

        and:
        eventDialog.cancel()

        then:
        eventDialog.displayed == false
        cancelEditConfirmationDialog.size() != 0
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



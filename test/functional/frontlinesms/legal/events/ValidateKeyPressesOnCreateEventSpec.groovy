package frontlinesms.legal.events

import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.events.NewEventPage

class ValidateKeyPressesOnCreateEventSpec extends FrontlinesmsLegalGebSpec {

    def "should enable only tab key on event date field"() {
        given: to NewEventPage

        when: date.click()
        and: date << "a!dfj-*#"

        then: date.value() == ""
    }

    def "should not allow non-numeric keys on event start time field"() {
        given: to NewEventPage

        when: startTimeField.click()
        and: startTimeField << "Aa-!@)("

        then: startTimeField.value() == "12:00AM"
    }

    def "should not allow non-numeric keys on event end time field"() {
        given: to NewEventPage

        when: endTimeField.click()
        and: endTimeField << "Aa-!@)("

        then: endTimeField.value() == "12:00AM"
    }

    def "focus should remain on the event title field on pressing enter key"() {
        given: to NewEventPage

        when: eventTitle.click()
        and: eventTitle << "\r"

        then: elementInFocusIs(eventTitle)
    }
}

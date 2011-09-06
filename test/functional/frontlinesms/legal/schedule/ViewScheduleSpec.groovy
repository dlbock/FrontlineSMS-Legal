package frontlinesms.legal.schedule

import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.events.NewEventPage
import frontlinesms.legal.functionaltests.pages.schedule.SchedulePage

class ViewScheduleSpec extends FrontlinesmsLegalGebSpec {

    def 'should display events from the database'() {
        given:
        to NewEventPage

        when:
        eventTitle = "super SPECIAL event!"
        date.setDate(16)
        startTimeField = "08:09AM"
        endTimeField = "08:56AM"

        and:
        save.click()

        then:
        assert at(SchedulePage)
        assert events.collect {it -> it.text()}.contains("super SPECIAL event!")
    }
}

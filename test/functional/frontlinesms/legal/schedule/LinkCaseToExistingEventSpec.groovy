package frontlinesms.legal.schedule

import frontlinesms.legal.Case
import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.events.NewEventPage
import frontlinesms.legal.functionaltests.pages.schedule.SchedulePage

class LinkCaseToExistingEventSpec extends FrontlinesmsLegalGebSpec {

    def "should not display link case dialog when event details pop up is loaded"() {
        given:
        createEvent("fabio")
        to SchedulePage, "index"

        when:
        events()[0].click()

        then:
        linkCaseDialog.displayed == false
    }

    def "should display a dialog with list of cases when link case button is clicked"() {
        given:
        createEvent("fabio")
        to SchedulePage, "index"

        when:
        events()[0].click()
        linkCaseButton.click()

        then:
        linkCaseDialog.displayed == true
    }

    def "should close case dialog when cancel button is clicked on the dialog"() {
        given:
        createEvent("fabio")
        to SchedulePage, "index"

        when:
        events()[0].click()
        linkCaseButton.click()

        and:
        caseDialogCancelButton.click()

        then:
        linkCaseDialog.displayed == false
    }

    def "should display cases in the case dialog "() {

        setup:
        new Case(caseId: "1112", description: "ertyui").save(flush: true)
        new Case(caseId: "1113", description: "erdstyui").save(flush: true)

        when:
        createEvent("fabio")
        to SchedulePage, "index"

        and:
        events()[0].click()
        linkCaseButton.click()

        then:
        casesToLink.size() == 2
    }

    def "should display link button next to each case in the case dialog"() {
        setup:
        new Case(caseId: "1112", description: "ertyui").save(flush: true)
        new Case(caseId: "1113", description: "erdstyui").save(flush: true)

        when:
        createEvent("fabio")
        to SchedulePage, "index"

        and:
        events()[0].click()
        linkCaseButton.click()

        then:
        casesToLink.collect { it -> it.linkCase }.size() == 2
    }

    def "should append the selected case to the linked case table on the event details pop up"() {
        setup:
        new Case(caseId: "1112", description: "ertyui").save(flush: true)
        new Case(caseId: "1113", description: "erdstyui").save(flush: true)

        when:
        createEvent("fabio")
        to SchedulePage, "index"

        and:
        events()[0].click()
        linkCaseButton.click()

        and:
        linkToBeSelected[0].click()

        then:
        existingCaseList.size() == 1

    }

    def "an already linked case should not be linked again"(){
        setup:
        new Case(caseId: "1112", description: "ertyui").save(flush: true)
        new Case(caseId: "1113", description: "erdstyui").save(flush: true)

        when:
        createEvent("fabio")
        to SchedulePage, "index"

        and:
        events()[0].click()
        linkCaseButton.click()

        and:
        linkToBeSelected[0].click()

        and:
        linkCaseButton.click()

        and:
        linkToBeSelected[0].click()

        then:
        existingCaseList.size() == 1

    }

    private def createEvent(title) {
        to NewEventPage
        eventTitle = title
        date.setDate(6)
        startTimeField = "08:09AM"
        endTimeField = "08:56PM"
        save.click()
    }
}

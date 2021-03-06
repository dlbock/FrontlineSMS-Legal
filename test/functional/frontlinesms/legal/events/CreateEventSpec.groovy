package frontlinesms.legal.events

import frontlinesms.legal.Case
import frontlinesms.legal.LegalContact
import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.HomePage
import frontlinesms.legal.functionaltests.pages.events.NewEventPage
import org.openqa.selenium.Keys

class CreateEventSpec extends FrontlinesmsLegalGebSpec {
    def "should be able to navigate to the create event page from the menu"() {
        given: to HomePage

        when: createNewEvent.click()

        then: at(NewEventPage)
    }

    def "should show date picker when date field is focused"() {
        given: to NewEventPage

        when: date.openDatePicker()

        then: date.datePicker.displayed
    }

    def "should remove focus from date field after hittin enter key in date picker"() {
        given: to NewEventPage

        when: date.openDatePicker()
        and: date.selectCurrentDate()

        then: !elementInFocusIs(date)
    }

    def "when hours are typed in the StartTime text box then minutes should be set to zero"() {
        given: to NewEventPage

        when: startTimeField.click()

        and: startTimeField << "3"

        then: startTimeField.value() == "03:00AM"
    }

    def "when hours are typed in the EndTime text box then minutes should be set to zero"() {
        given: to NewEventPage

        when: endTimeField.click()

        and: endTimeField << "5"

        then: endTimeField.value() == "05:00AM"
    }

    def "when a time is set in the StartTime text box the EndTime should be auto set to one hour more"() {
        given: to NewEventPage

        when: startTimeField.click()

        and: startTimeField << "3"

        then: endTimeField.value() == "04:00AM"
    }

    def 'should hide cancel confirm dialog when no is clicked'() {
        given:
        to NewEventPage

        when:
        eventTitle = 'blah'
        cancel.click()

        and:
        cancelNo.click()

        then:
        eventCancelDialog.displayed == false
    }

    def 'should remain on Create event page when no is clicked on cancel confirm dialog'() {
        given:
        to NewEventPage

        when:
        eventTitle = 'blah'
        cancel.click()

        and:
        cancelNo.click()

        then:
        assert at(NewEventPage)
    }

    def 'should go to home page when yes is clicked on cancel confirm dialog'() {
        given:
        to NewEventPage

        when:
        eventTitle = 'blah'
        cancel.click()

        and:
        cancelYes.click()

        then:
        assert at(HomePage)
    }

    def "should show the search contact dialog box after pressing LINK CONTACT button"() {
        when:
        to NewEventPage
        and:
        linkContact()

        then:
        linkContactDialog.displayed == true
    }

    def "should display all the contacts filtered by the value in the search input of the search contact dialog"() {
        setup:
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "dev", primaryMobile: "55555").save(flush: true)

        when:
        to NewEventPage
        and:
        linkContact()
        and:
        linkContactDialog.searchFor("fab")

        then:
        linkContactDialog.contactsNotInSearchResults.size() == 1
    }

    def "should continue showing the search contact dialog box after pressing RETURN when searching for a contact"() {
        setup:
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "dev", primaryMobile: "55555").save(flush: true)

        when:
        to NewEventPage
        and:
        linkContact()
        and:
        linkContactDialog.searchFor("\r")

        then:
        linkContactDialog.displayed == true
    }

    def "should clear the search input when the dialog box is open, closed, reopened"() {
        setup:
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "dev", primaryMobile: "55555").save(flush: true)

        when:
        to NewEventPage
        and:
        linkContact()
        and:
        linkContactDialog.searchFor("fab")
        and:
        linkContactDialog.cancel()
        and:
        linkContact()

        then:
        linkContactDialog.searchbox.value() == ""
    }

    def "should display all the contacts when the dialog box is reopened after a previous filter"() {
        setup:
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "dev", primaryMobile: "55555").save(flush: true)

        when:
        to NewEventPage
        and:
        linkContact()
        and:
        linkContactDialog.searchFor("fab")
        and:
        linkContactDialog.cancel()
        and:
        linkContact()

        then:
        linkContactDialog.contactsNotInSearchResults.size() == 0
    }

    def "should display a Link Case button"() {

        when:
        to NewEventPage

        then:
        linkCaseToEventButton.size() == 1
    }

    def "should not display link case dialog when page is loaded"() {
        when:
        to NewEventPage

        then:
        linkCaseDialog.displayed == false
    }

    def "should display a dialog with list of cases when link case button is clicked"() {
        when:
        to NewEventPage

        and:
        linkCaseToEventButton.click()

        then:
        linkCaseDialog.displayed == true
    }

    def "should close case dialog when cancel button is clicked on the dialog"() {
        when:
        to NewEventPage

        and:
        linkCaseToEventButton.click()

        and:
        caseDialogCancelButton.click()

        then:
        linkCaseDialog.displayed == false
    }

    def "should display cases in the case dialog "() {

        setup:
        new Case(caseId: "1112", caseTitle: "CaseTitle 1",description: "ertyui").save(flush: true)
        new Case(caseId: "1113", caseTitle: "CaseTitle 2",description: "erdstyui").save(flush: true)

        when:
        to NewEventPage

        and:
        linkCaseToEventButton.click()

        then:
        casesToLink.collect {it -> it.linkCase }.size() == 2
    }

    def "should display link button next to each case in the case dialog"() {
        setup:
        new Case(caseId: "1112", caseTitle: "CaseTitle 1",description: "ertyui").save(flush: true)
        new Case(caseId: "1113", caseTitle: "CaseTitle 2",description: "erdstyui").save(flush: true)

        when:
        to NewEventPage

        and:
        linkCaseToEventButton.click()

        then:
        casesToLink.collect { it -> it.linkCase }.size() == 2
    }

    def "should append the selected case with case title to the linked case table on the create event page"(){
        setup:
        new Case(caseId: "1112", caseTitle: "CaseTitle 1", description: "ertyui").save(flush: true)
        new Case(caseId: "1113", caseTitle: "CaseTitle 2", description: "erdstyui").save(flush: true)

        when:
        to NewEventPage

        and:
        linkCaseToEventButton.click()

        and:
        casesToLink[0].linkCase.click()

        then:
        oneContactIsDisplayed
        linkedCasesTable[0].caseTitle == "CaseTitle 1"
    }

    def "an already linked case should not be linked again"() {
        setup:
        new Case(caseId: "1112", caseTitle: "CaseTitle 1",description: "ertyui").save(flush: true)
        new Case(caseId: "1113", caseTitle: "CaseTitle 2",description: "erdstyui").save(flush: true)

        when:
        to NewEventPage

        and:
        linkCaseToEventButton.click()

        and:
        casesToLink[0].linkCase.click()

        and:
        linkCaseToEventButton.click()

        and:
        casesToLink[0].linkCase.click()

        then:
        assert NewEventPage

        and:
        oneContactIsDisplayed
    }
}

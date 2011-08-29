package frontlinesms.legal.events

import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.HomePage
import frontlinesms.legal.functionaltests.pages.events.NewEventPage
import frontlinesms.legal.functionaltests.pages.schedule.SchedulePage
import spock.lang.Ignore
import frontlinesms.legal.LegalContact

class CreateEventSpec extends FrontlinesmsLegalGebSpec {
     def "should be able to navigate to the create event page from the menu"() {
        given: to HomePage

        when: createNewEvent.click()

        then: at(NewEventPage)
    }

    def "should show date picker when date field is focused"(){
        given: to NewEventPage

        when: dateFieldSelected

        then: datePicker.present
    }


    def "when hours are typed in the StartTime text box then minutes should be set to zero"(){
        given: to NewEventPage

        when: startTimeField.click()

        and: startTimeField << "3"

        then: startTimeField.value() == "03:00AM"
    }

        def "when hours are typed in the EndTime text box then minutes should be set to zero"(){
        given: to NewEventPage

        when: endTimeField.click()

        and: endTimeField << "5"

        then: endTimeField.value() == "05:00AM"
    }


    def 'should hide cancel confirm dialog when no is clicked'(){
        given:
        to NewEventPage

        when:
        eventTitle ='blah'
        cancel.click()

        and:
        cancelNo.click()

        then:
        eventCancelDialog.displayed == false
    }

    def 'should remain on Create event page when no is clicked on cancel confirm dialog'(){
        given:
        to NewEventPage

        when:
        eventTitle ='blah'
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
        eventTitle ='blah'
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
        clickLinkContact.click()

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
        clickLinkContact.click()
        and:
        contactNameSearch.value("fab")
        sleep(500)

        then:
        contactLinkNotVisible().size() == 1
    }

    def "should continue showing the search contact dialog box after pressing RETURN when searching for a contact"() {
        setup:
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "dev", primaryMobile: "55555").save(flush: true)

        when:
        to NewEventPage
        and:
        clickLinkContact.click()
        and:
        contactNameSearch.value("\r")

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
        clickLinkContact.click()
        and:
        contactNameSearch.value("fab")
        sleep(500)
        and:
        clickDialogCancelButton.click()
        and:
        clickLinkContact.click()

        then:
        contactNameSearch.value() == ""
    }

     def "should display all the contacts when the dialog box is reopened after a previous filter"() {
        setup:
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "dev", primaryMobile: "55555").save(flush: true)

        when:
        to NewEventPage
        and:
        clickLinkContact.click()
        and:
        contactNameSearch.value("fab")
        sleep(500)
        and:
        clickDialogCancelButton.click()
        and:
        clickLinkContact.click()

        then:
        contactLinkNotVisible().size() == 0
    }




}

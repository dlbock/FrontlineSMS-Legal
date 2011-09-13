package frontlinesms.legal.cases

import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.cases.SearchCasePage
import frontlinesms.legal.functionaltests.pages.cases.ShowCasePage
import frontlinesms.legal.functionaltests.pages.contact.ShowLegalContactPage
import java.sql.Time
import frontlinesms.legal.*
import frontlinesms.legal.functionaltests.pages.events.NewEventPage

class ShowCaseSpec extends FrontlinesmsLegalGebSpec {

    def "should display details of the selected case in the show case page"() {
        given:
        new Case(caseId: "testCaseid", description: "whatever").save()

        when: to ShowCasePage, "testCaseid"

        then: caseId == "testCaseid"

        and: description == "whatever"

        and: caseActive.value() == "on"
    }

    def "should display all contacts in contact list table in pop-up dialog on load"() {
        setup:
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "dev", primaryMobile: "55555").save(flush: true)
        new Case(caseId: "1112").save(flush: true)

        when:
        to ShowCasePage, "1112"
        and:
        linkContact.click()

        then:
        contactListInPopUp.collect { it -> it.name }.contains("fabio")

        and:
        contactListInPopUp.collect { it -> it.primaryMobile}.contains("22222")

        and:
        contactListInPopUp.collect { it -> it.name }.contains("dev")

        and:
        contactListInPopUp.collect { it -> it.primaryMobile }.contains("55555")
    }

    def "should not update case id that already exists"() {
        setup:
        new Case(caseId: "1112", description: "ertyui").save(flush: true)
        new Case(caseId: "1123", description: "ertyui").save(flush: true)

        when:
        to ShowCasePage, "1112"

        and:
        caseId = "1123"

        and:
        updateCaseButton.click()

        then:
        errorMessage == "Case ID already exists. Please enter a unique Case ID."
    }

    def "should show the same description on trying to update case id that already exists"() {
        setup:
        new Case(caseId: "1112", description: "ertyui").save(flush: true)
        new Case(caseId: "1123", description: "ertyui").save(flush: true)

        when:
        to ShowCasePage, "1112"

        and:
        caseId = "1123"
        description = "new description"

        and:
        updateCaseButton.click()

        then:
        at ShowCasePage
        description == "new description"

        and:
        caseId == "1112"
    }

    def "should not allow update case if case id is blank"() {
        setup:
        new Case(caseId: "1112", description: "ertyui").save(flush: true)

        when:
        to ShowCasePage, "1112"

        and:
        caseId = ""

        and:
        updateCaseButton.click()

        then:
        errorMessage == "Case ID required"
    }

    def "should show the same case status on trying to update case id that already exists"() {
        setup:
        new Case(caseId: "1112", description: "ertyui").save(flush: true)
        new Case(caseId: "1123", description: "ertyui").save(flush: true)

        when:
        to ShowCasePage, "1112"

        and:
        caseId = "1123"
        description = "new description"
        caseActive.value("off")

        and:
        updateCaseButton.click()

        then:
        at ShowCasePage
        caseActive.value() == null
    }

    def "should display all the contacts filtered by the value in the search input of the search contact dialog"() {
        setup:
        new Case(caseId: "1112", description: "ertyui").save(flush: true)
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "dev", primaryMobile: "55555").save(flush: true)

        when:
        to ShowCasePage, "1112"

        and:
        linkContact.click()

        and:
        contactNameSearch.value("fab")
        sleep(500)

        then:
        contactLinkNotVisible().size() == 1
    }

    def "should continue showing the search contact dialog box after pressing RETURN when searching for a contact"() {
        setup:
        new Case(caseId: "1112", description: "ertyui").save(flush: true)
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "dev", primaryMobile: "55555").save(flush: true)

        when:
        to ShowCasePage, "1112"

        and:
        linkContact.click()

        and:
        contactNameSearch.value("\r")

        then:
        linkContactDialog.displayed == true
    }

    def "should clear the search input when the dialog box is open, closed, reopened"() {
        setup:
        new Case(caseId: "1112", description: "ertyui").save(flush: true)
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "dev", primaryMobile: "55555").save(flush: true)

        when:
        to ShowCasePage, "1112"

        and:
        linkContact.click()

        and:
        contactNameSearch.value("fab")
        sleep(500)

        and:
        linkContactDialogCancelButton.click()

        and:
        linkContact.click()

        then:
        contactNameSearch.value() == ""
    }

    def "should display all the contacts when the dialog box is reopened after a previous filter"() {
        setup:
        new Case(caseId: "1112", description: "ertyui").save(flush: true)
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "dev", primaryMobile: "55555").save(flush: true)

        when:
        to ShowCasePage, "1112"
        and:
        linkContact.click()
        and:
        contactNameSearch.value("fab")
        sleep(500)
        and:
        linkContactDialogCancelButton.click()
        and:
        linkContact.click()

        then:
        contactLinkNotVisible().size() == 0
    }

    def "should show future and past event linked with case"() {
        setup:
        def id = createFutureAndPastEventsAndLinkCases()

        when:
        to ShowCasePage, id

        then:
        pastEventsTable.collect { it -> it.title }[0] == "Past"
        and:
        futureEventsTable.collect { it -> it.title }[0] == "Future"
    }

    def "should show current and past event linked with case"() {
        setup:
        def id = createCurrentAndPastEventsAndLinkCases()

        when:
        to ShowCasePage, id

        then:
        pastEventsTable.collect { it -> it.title }[0] == "Past"
        and:
        currentEventsTable.collect { it -> it.title }[0] == "Current"
    }

    def "should not show contact in the contact table when cancel is clicked on the relationship dialog"() {
        setup:
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        new LegalContact(name: "dev", primaryMobile: "55555").save(flush: true)
        def caseOne = new Case(caseId: "1112", description: "ertyui")
        caseOne.save(flush: true)

        when:
        to ShowCasePage, caseOne.caseId

        and:
        linkContact.click()
        contactListInPopUp[0].click()
        relationshipCancelButton.click()

        then:
        linkContactDialog.present
        sizeOflinkedContactsTable == 0
    }

    def "should show the contact in the contact table when the contact is selected in the link contact dialog"() {
        setup:
        new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)
        def caseOne = new Case(caseId: "1112", description: "ertyui")
        caseOne.save(flush: true)

        when:
        to ShowCasePage, caseOne.caseId

        and:
        linkContact.click()
        contactListInPopUp[0].click()
        relationshipConfirmButton.click()

        then:
        sizeOflinkedContactsTable == 1
    }

    def 'should not delete when NO is clicked on delete confirmation dialog'() {
        given:
        new Case(caseId: "123", description: "test").save(flush: true)
        new Case(caseId: "321", description: "test2").save(flush: true)
        to ShowCasePage, "123"

        when:
        deleteButton.click()

        and:
        deleteNo.click()

        then:
        at(ShowCasePage)
    }

    def 'should stay on search page when YES is clicked in delete confirmation dialog'() {
        given:
        new Case(caseId: "123", description: "test").save(flush: true)
        new Case(caseId: "321", description: "test2").save(flush: true)

        to ShowCasePage, "123"

        when:
        deleteButton.click();
        deleteYes.click();
        waitFor { !deleteDialog.isVisible() }

        then:
        assert at(SearchCasePage)
    }

    def 'should not discard the changes made to case details when the user clicks NO on the Cancel Case Changes dialog'() {
        setup:
        new Case(caseId: "1112", description: "ertyui").save(flush: true)

        when:
        to ShowCasePage, "1112"

        and:
        caseId = "1113"

        and:
        cancelButton.click()

        then:
        cancelDialog.displayed == true

        and:
        cancelNo.click()

        then:
        caseId == "1113"
    }

    def 'should discard the changes made to case details when the user clicks YES on the Cancel Case Changes dialog'() {
        setup:
        def caseOne = new Case(caseId: "1112", description: "ertyui").save(flush: true)
        def contactOne = new LegalContact(name: "one", primaryMobile: "11111").save(flush: true)
        CaseContacts.link(caseOne, contactOne, "click link button")

        def contactTwo = new LegalContact(name: "two", primaryMobile: "22222").save(flush: true)

        when:
        to ShowCasePage, "1112"

        and:
        caseId = "1113"

        and:
        unlinkButton[0].click()

        and:
        linkContact.click()
        contactListInPopUp[1].click()
        relationshipConfirmButton.click()

        and:
        cancelButton.click()

        then:
        cancelDialog.displayed == true

        and:
        cancelYes.click()

        when:
        to ShowCasePage, "1112"

        then:
        Case.findByCaseId("1113") == null
        def originalCase = Case.findByCaseId("1112")
        originalCase.caseId == "1112"
        sizeOflinkedContactsTable == 1
        linkedContactsTable.collect { it -> it.name }[0] == "one"
    }

    def "should not show contact linked to the case in the contact table when user clicks on unlink"() {
        given:
        def caseOne = new Case(caseId: "1112", description: "ertyui")
        caseOne.save(flush: true)
        def contactOne = new LegalContact(name: "fabio", primaryMobile: "22222")
        contactOne.save(flush: true)
        CaseContacts.link(caseOne, contactOne, "click link button")

        when:
        to ShowCasePage, "1112"

        then:
        sizeOflinkedContactsTable == 1

        and:
        unlinkButton[0].click()

        then:
        sizeOflinkedContactsTable == 0
    }

    def "should update case details when update is clicked"() {
        given:
        def caseOne = new Case(caseId: "1112", description: "ertyui")
        caseOne.save(flush: true)
        def contactOne = new LegalContact(name: "fabio", primaryMobile: "22222")
        contactOne.save(flush: true)
        CaseContacts.link(caseOne, contactOne, "click link button")

        when:
        to ShowCasePage, "1112"

        and:
        caseId = "1113"
        description = "update"
        caseActive.value("on")
        unlinkButton[0].click()
        updateCaseButton.click()

        then:
        caseId == "1113"
        description == "update"
        caseActive.value() == "on"
        sizeOflinkedContactsTable == 0
        statusMessage == "Case details updated"
    }

    def "should show linked contact in the case details page"() {
        given:
        def contact = new LegalContact(id: "1", name: "fabio", primaryMobile: "22222").save(flush: true)
        def caseOne = new Case(caseId: "1112", description: "ertyui")
        caseOne.save(flush: true)
        to ShowLegalContactPage, contact.id

        when:
        linkCaseButton.click()
        casesToLink[0].linkCaseButton.click()
        relationshipInput << "Victim"
        relationshipConfirmButton.click()
        updateButton.click()

        and:
        to ShowCasePage, caseOne.caseId

        then:
        sizeOflinkedContactsTable == 1
    }

    def "should show previous and upcoming events when they exist"() {
        given:
        def caseOne = new Case(caseId: "1001", description: "case linked event").save(flush: true)

        and:
        to NewEventPage
        createEvent("previous event", 12)
        createEvent("upcoming event", 14)

        when:
        to ShowCasePage, caseOne.caseId

        then:
        pastEventsTable.size() == 1
        futureEventsTable.size() == 1
    }

    def "should not show any event table when there is no events linked"() {
        given:
        def caseOne = new Case(caseId: "1001", description: "case linked event").save(flush: true)

        when:
        to ShowCasePage, caseOne.caseId

        then:
        pastEventsTable == []
        futureEventsTable == []
    }

    private def createFutureAndPastEventsAndLinkCases() {
        def yearOffsetForDate = 1900
        def caseone = new Case(caseId: "1112", description: "ertyui")
        caseone.save(flush: true)
        def pastEvent = new Event(eventTitle: "Past", dateFieldSelected: new Date(1990 - yearOffsetForDate, 8, 12), startTimeField: new Time(12, 30, 0), endTimeField: new Time(13, 30, 0))
        pastEvent.save(flush: true)
        def futureEvent = new Event(eventTitle: "Future", dateFieldSelected: new Date(2020 - yearOffsetForDate, 8, 12), startTimeField: new Time(12, 30, 0), endTimeField: new Time(13, 30, 0))
        futureEvent.save(flush: true)
        EventCase.link(pastEvent, caseone)
        EventCase.link(futureEvent, caseone)
        caseone.caseId
    }

    private def createCurrentAndPastEventsAndLinkCases() {
        def yearOffsetForDate = 1900
        def caseone = new Case(caseId: "1112", description: "ertyui")
        caseone.save(flush: true)
        def pastEvent = new Event(eventTitle: "Past", dateFieldSelected: new Date(1990 - yearOffsetForDate, 8, 12), startTimeField: new Time(12, 30, 0), endTimeField: new Time(13, 30, 0))
        pastEvent.save(flush: true)
        def futureEvent = new Event(eventTitle: "Future", dateFieldSelected: new Date(2020 - yearOffsetForDate, 8, 12), startTimeField: new Time(12, 30, 0), endTimeField: new Time(13, 30, 0))
        futureEvent.save(flush: true)
        def startDate = new Date()
        def startTime = new Time(startDate.getTime() - 240000)
        def endTime = new Time(startDate.getTime() + 240000)
        def currentEvent = new Event(eventTitle: "Current", dateFieldSelected: startDate, startTimeField: startTime, endTimeField: endTime)
        currentEvent.save(flush: true)
        EventCase.link(pastEvent, caseone)
        EventCase.link(futureEvent, caseone)
        EventCase.link(currentEvent, caseone)
        caseone.caseId
    }

    private def createEvent(title, eventDate) {
        to NewEventPage
        eventTitle = title
        date.setDate(eventDate)
        startTimeField = "08:09AM"
        endTimeField = "08:56PM"
        linkCaseToEventButton.click()
        casesToLink[0].linkCase.click()
        save.click()
    }
}

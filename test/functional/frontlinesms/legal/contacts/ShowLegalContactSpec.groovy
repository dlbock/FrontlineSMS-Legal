package frontlinesms.legal.contacts

import frontlinesms.legal.Event
import frontlinesms.legal.EventContact
import frontlinesms.legal.LegalContact
import frontlinesms.legal.functionaltests.FrontlinesmsLegalGebSpec
import frontlinesms.legal.functionaltests.pages.contact.ShowLegalContactPage
import java.sql.Time
import frontlinesms.legal.Case
import frontlinesms.legal.functionaltests.pages.contact.CreateLegalContactPage
import frontlinesms.legal.functionaltests.pages.cases.ShowCasePage

class ShowLegalContactSpec extends FrontlinesmsLegalGebSpec {

    def "should show contact details"() {
        given:
        new Case(caseId: "123", description: "test").save(flush: true)
        new Case(caseId: "321", description: "test2").save(flush: true)
        to CreateLegalContactPage

        when:
        name << "Bob"
        primaryMobile << "8675309"
        notes << "Testing"
        linkCaseButton.click()
        caseIdSearch << "123"
        casesToLink[0].linkCaseButton.click()
        relationshipInput << "Victim"
        relationshipConfirmButton.click()
        save.click()

        then:
        assert at(ShowLegalContactPage)
        name == "Bob"
        primaryMobile == "8675309"
        notes.text() == "Testing"
        linkedCasesTable[0].caseId == "123"
        linkedCasesTable[0].relationship == "Victim"
        linkedCasesTable[0].unlink.displayed
    }

    def "Update button should be disabled when no changes are made to the contact details"() {
        given:
        new Case(caseId: "123", description: "test").save(flush: true)
        new Case(caseId: "321", description: "test2").save(flush: true)
        to CreateLegalContactPage

        when:
        name << "Bob"
        primaryMobile << "8675309"
        notes << "Testing"
        linkCaseButton.click()
        caseIdSearch << "123"
        casesToLink[0].linkCaseButton.click()
        relationshipInput << "Victim"
        relationshipConfirmButton.click()

        save.click()

        then:
        at(ShowLegalContactPage)
        updateContactButtonIsDisabled == 1
    }

    def "Update button should be enabled when changes are made to the contact details"() {
        given:
        new Case(caseId: "123", description: "test").save(flush: true)
        new Case(caseId: "321", description: "test2").save(flush: true)
        to CreateLegalContactPage

        when:
        name << "Bob"
        primaryMobile << "8675309"
        notes << "Testing"
        linkCaseButton.click()
        caseIdSearch << "123"
        casesToLink[0].linkCaseButton.click()
        relationshipInput << "Victim"
        relationshipConfirmButton.click()

        save.click()
        name << "Bob Test"

        then:
        at(ShowLegalContactPage)
        updateContactButtonIsDisabled == 0
    }

    def "Update button should be enabled when cases are linked to the contact"() {
        given:
        new Case(caseId: "123", description: "test").save(flush: true)
        new Case(caseId: "321", description: "test2").save(flush: true)
        to CreateLegalContactPage
        name << "Bob"
        primaryMobile << "8675309"
        notes << "Testing"
        save.click()

        when:
        at(ShowLegalContactPage)
        linkCaseButton.click()
        casesToLink[0].linkCaseButton.click()
        relationshipInput << "Victim"
        relationshipConfirmButton.click()

        then:
        updateContactButtonIsDisabled == 0
    }

    def "Update button should be enabled when cases are unlinked from the contact"() {
        given:
        new Case(caseId: "123", description: "test").save(flush: true)
        new Case(caseId: "321", description: "test2").save(flush: true)
        to CreateLegalContactPage
        name << "Bob"
        primaryMobile << "8675309"
        notes << "Testing"
        linkCaseButton.click()
        caseIdSearch << "123"
        casesToLink[0].linkCaseButton.click()
        relationshipInput << "Victim"
        relationshipConfirmButton.click()
        save.click()

        when:
        at(ShowLegalContactPage)
        linkedCasesTable[0].unlink.click()

        then:
        updateContactButtonIsDisabled == 0
    }

    def "should show future and past event linked with legal contact"() {
        setup:
        def id = createFutureAndPastEventsAndLinkContacts()

        when:
        to ShowLegalContactPage, id

        then:
        pastEventsTable.first().title == "Past"
        and:
        futureEventsTable.first().title == "Future"
    }

    def "should show current and past event linked with legal contact"() {
        setup:
        def id = createCurrentAndPastEventsAndLinkContacts()

        when:
        to ShowLegalContactPage, id

        then:
        pastEventsTable.first().title == "Past"
        and:
        currentEventsTable.first().title == "Current"
    }

    def "should continue showing the search contact dialog box after pressing RETURN when searching for a contact"() {
        setup:
        new Case(caseId: "1112", description: "ertyui").save(flush: true)
        new Case(caseId: "1123", description: "ertooo").save(flush: true)
        def contact1 = new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)

        when:
        to ShowLegalContactPage, contact1.id
        and:
        linkCaseButton.click()
        and:
        caseIdSearch.value("\r")
        sleep(500)

        then:
        caseLinkNotVisible.size() == 0
    }

    def "should not link a case that is already linked to the contact and should stay on the link case pop-up"() {
        given:
        new Case(caseId: "123", caseTitle: "Case Title 1", description: "test").save(flush: true)
        new Case(caseId: "321", caseTitle: "Case Title 2", description: "test2").save(flush: true)
        def contact1 = new LegalContact(name: "fabio", primaryMobile: "22222").save(flush: true)

        when:
        to ShowLegalContactPage, contact1.id
        linkCaseButton.click()
        casesToLink[0].linkCaseButton.click()
        relationshipInput << "Victim"
        relationshipConfirmButton.click()
        linkCaseButton.click()
        casesToLink[0].linkCaseButton.click()

        then:
        linkCaseDialog.present
    }

    def "should show linked case on contact details page when contact is linked from case detail page "() {
        given:
        def case1 = new Case(caseId: "123", caseTitle: "Case Title 1", description: "test").save(flush: true)
        def contact1 = new LegalContact(name: "batman", primaryMobile: "123454321").save(flush: true)

        to ShowCasePage, case1.caseId

        when:
        linkContact.click()
        contactListInPopUp[0].link()
        sleep(5000)
        relationshipConfirmButton.click()
        sleep(5000)
        updateCaseButton.click()

        and:
        to ShowLegalContactPage, contact1.id

        then:
        linkedCasesTable.collect { it -> it.caseTitle }[0] == "Case Title 1"
    }

    private def createFutureAndPastEventsAndLinkContacts() {
        def yearOffsetForDate = 1900
        def legalContact = new LegalContact(name: 'fabio', primaryMobile: '1234567')
        legalContact.save(flush: true)
        def pastEvent = new Event(eventTitle: "Past", dateFieldSelected: new Date(1990 - yearOffsetForDate, 8, 12), startTimeField: new Time(12, 30, 0), endTimeField: new Time(13, 30, 0))
        pastEvent.save(flush: true)
        def futureEvent = new Event(eventTitle: "Future", dateFieldSelected: new Date(2020 - yearOffsetForDate, 8, 12), startTimeField: new Time(12, 30, 0), endTimeField: new Time(13, 30, 0))
        futureEvent.save(flush: true)
        EventContact.link(pastEvent, legalContact)
        EventContact.link(futureEvent, legalContact)
        legalContact.id
    }

    private def createCurrentAndPastEventsAndLinkContacts() {
        def yearOffsetForDate = 1900
        def legalContact = new LegalContact(name: 'temptation', primaryMobile: '987654')
        legalContact.save(flush: true)
        def pastEvent = new Event(eventTitle: "Past", dateFieldSelected: new Date(1990 - yearOffsetForDate, 8, 12), startTimeField: new Time(12, 30, 0), endTimeField: new Time(13, 30, 0))
        pastEvent.save(flush: true)
        def futureEvent = new Event(eventTitle: "Future", dateFieldSelected: new Date(2020 - yearOffsetForDate, 8, 12), startTimeField: new Time(12, 30, 0), endTimeField: new Time(13, 30, 0))
        futureEvent.save(flush: true)
        def startDate = new Date()
        def startTime = new Time(startDate.getTime() - 240000)
        def endTime = new Time(startDate.getTime() + 240000)
        def currentEvent = new Event(eventTitle: "Current", dateFieldSelected: startDate, startTimeField: startTime, endTimeField: endTime)
        currentEvent.save(flush: true)

        EventContact.link(pastEvent, legalContact)
        EventContact.link(futureEvent, legalContact)
        EventContact.link(currentEvent, legalContact)
        legalContact.id

    }
}

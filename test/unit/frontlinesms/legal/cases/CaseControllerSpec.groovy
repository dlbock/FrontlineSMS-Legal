package frontlinesms.legal.cases

import grails.plugin.spock.ControllerSpec
import frontlinesms.legal.Case
import frontlinesms.legal.LegalContact
import frontlinesms2.Contact
import frontlinesms.legal.CaseContacts
import frontlinesms.legal.Event
import java.sql.Time
import frontlinesms.legal.EventCase

class CaseControllerSpec extends ControllerSpec {

    def 'should create action should redirect to Case detail page'() {
        setup:
        mockDomain(Case)
        mockDomain(CaseContacts)

        controller.params.caseId = '1234'
        controller.params.description = 'hagsdhs'

        when:
        controller.save()

        then:
        redirectArgs == [action: "show", params: [id: "1234"]]
    }

    def "should save case with Id and description"() {
        setup:
        def cases = []
        mockDomain(Case, cases)
        mockDomain(CaseContacts)
        controller.params.caseId = '1234'
        controller.params.description = 'hagsdhs'

        when:
        controller.save()

        then:
        Case.count() == 1
    }

    def "should save contact link when linked on create case"() {
        setup:
        mockDomain(Case)
        mockDomain(CaseContacts)
        mockDomain(LegalContact, [new LegalContact(id: 1, name: "John Doe", primaryMobile: "435352", notes: "hii")])

        when:
        controller.params.caseId = "2"
        controller.params.caseLinkedContacts = "{'1':'something'}"

        and:
        controller.save()

        then:
        CaseContacts.count() == 1
    }

    def 'should display error message and redirect to create when case id is blank'() {
        given:
        mockDomain(Case)
        controller.params.description = '1234'

        when:
        controller.save()

        then:
        redirectArgs == [action: "create", params: [description: "1234"]]
        controller.flash.error == "Case ID is required"
    }

    def 'should display an error when creating a case with duplicate id'() {
        given:
        def cases = []
        cases.add(new Case(caseId: '1234'))
        mockDomain(Case, cases)

        controller.params.caseId = '1234'

        when:
        controller.save()

        then:
        controller.flash.error == 'Case ID already exists. Please enter a unique Case ID.'
    }

    def 'should display list of cases matching search criteria'() {
        given:
        def casesList = []
        casesList.add(new Case(caseId: '456'))
        mockDomain(Case, casesList)


        controller.params.caseId = '456'
        when:
        def foundCases = controller.search()

        then:
        foundCases['foundCase'].get(0).caseId == '456'

    }

    def 'should display case details given case id'() {
        given:
        def newCase = new Case(caseId: "1234")
        def contactList = []
        mockDomain(CaseContacts)
        mockDomain(Case, [newCase])
        mockDomain(LegalContact, contactList)
        controller.params.id = "1234"

        when:
        def displayedCase = controller.show()['caseToDisplay']

        then:
        displayedCase.caseId == newCase.caseId
    }

    def 'should display all the contacts when the popup appears'() {
        given:
        def newCase = new Case(caseId: "4567")
        mockDomain(Case, [newCase])
        mockDomain(CaseContacts)
        controller.params.id = "4567"
        def newContact = [new Contact()]
        mockDomain(Contact, newContact)

        when:
        def models = controller.show()

        then:
        models['contactList'] == newContact
    }

    def 'should update case details'() {
        setup:
        def existingCase = new Case(caseId: '12344', description: 'hiiii')
        mockDomain(Case, [existingCase])
        mockDomain(CaseContacts)
        controller.params.currentId = existingCase.id
        controller.params.caseId = '12344'
        controller.params.description = 'hagsdhs'
        controller.params.caseActive = false
        when:
        controller.update()

        then:
        existingCase.active == false
    }

    def 'should display a message when case is successfully deleted'() {
        given:
        def cases = []
        cases.add(new Case(caseId: '1234'))
        mockDomain(Case, cases)
        mockDomain(EventCase)

        controller.params.id = '1234'

        when:
        controller.delete()

        then:
        controller.flash.message == "Case deleted."
    }


    def 'should display an error when deleting a case that does not exist'() {
        given:
        def cases = []
        cases.add(new Case(caseId: '1234'))
        mockDomain(Case, cases)
        mockDomain(EventCase)

        controller.params.id = 'NaN'

        when:
        controller.delete()

        then:
        controller.flash.warning == "Case not found."
    }

    def "should delete case from database when case is deleted"() {
        setup:
        def cases = [new Case(caseId: '1234')]
        mockDomain(EventCase)
        mockDomain(Case, cases)
        controller.params.id = "1234"

        when:
        controller.delete()

        then:
        assert Case.count() == 0
    }

    def "should be able to unlink a case which is linked to some event before deleting it"(){
        setup:
        def newEvent = [new Event(eventTitle: "Test", dateFieldSelected: new Date("July 12,2011"), startTimeField: Time.valueOf("09:00:00"),endTimeField: Time.valueOf("10:00:00"))]
        mockDomain(Event, newEvent)

        def cases = [new Case(caseId: '1234')]
        mockDomain(Case, cases)
        mockDomain(EventCase)
        EventCase.link(newEvent[0], cases[0])
        CaseController controller = new CaseController()
        controller.params.id = cases[0].id

        when:
        controller.delete()

        then:
        assert EventCase.findAllByEvent(newEvent).size() == 0
    }

    def 'should return past and future event'() {
        def yearOffsetForDate = 1900 //To fix bug in java.sql.Date year attribute
        def cases = new Case(caseId: '1234', description: 'hello')

        def pastEvent = new Event(eventTitle: "Past", dateFieldSelected: new Date(1990 - yearOffsetForDate, 7, 3), startTimeField: new Time(13, 50, 0), endTimeField: new Time(15, 30, 0))
        def futureEvent = new Event(eventTitle: "Future", dateFieldSelected: new Date(2020 - yearOffsetForDate, 7, 3), startTimeField: new Time(13, 50, 0), endTimeField: new Time(15, 30, 0))

        def pastEventCase = new EventCase(event: pastEvent, eventCase: cases)
        def futureEventCase = new EventCase(event: futureEvent, eventCase: cases)
        def eventCaseList = [pastEventCase, futureEventCase]

        cases.linkedEvents = eventCaseList
        mockDomain(Case, [cases])
        mockDomain(LegalContact, [])
        mockDomain(EventCase, eventCaseList)
        controller.params.id = cases.caseId

        when:
        def models = controller.show()

        then:
        models["pastEvents"].contains(pastEvent)
        models["futureEvents"].contains(futureEvent)
    }

    def 'should return overlapping past events'() {
        given:
        def yearOffsetForDate = 1900
        def cases = new Case(caseId: '1234', description: 'hello')

        def pastEvent1 = new Event(eventTitle: "Past1", dateFieldSelected: new Date(1990 - yearOffsetForDate, 7, 3), startTimeField: new Time(13, 50, 0), endTimeField: new Time(15, 30, 0))
        def pastEvent2 = new Event(eventTitle: "Past2", dateFieldSelected: new Date(1990 - yearOffsetForDate, 7, 3), startTimeField: new Time(13, 50, 0), endTimeField: new Time(15, 30, 0))

        def pastEventCase1 = new EventCase(event: pastEvent1, eventCase: cases)
        def pastEventCase2 = new EventCase(event: pastEvent2, eventCase: cases)
        def eventCaseList = [pastEventCase1, pastEventCase2]

        cases.linkedEvents = eventCaseList
        mockDomain(Case, [cases])
        mockDomain(LegalContact, [])
        mockDomain(EventCase, eventCaseList)
        controller.params.id = cases.caseId

        when:
        def models = controller.show()

        then:
        models["pastEvents"].contains(pastEvent1)
        models["pastEvents"].contains(pastEvent2)
    }

    def 'should return overlapping future events'() {
        def yearOffsetForDate = 1900
        def cases = new Case(caseId: '1234', description: 'hello')

        def futureEvent1 = new Event(eventTitle: "Future1", dateFieldSelected: new Date(2020 - yearOffsetForDate, 7, 3), startTimeField: new Time(13, 50, 0), endTimeField: new Time(15, 30, 0))
        def futureEvent2 = new Event(eventTitle: "Future2", dateFieldSelected: new Date(2020 - yearOffsetForDate, 7, 3), startTimeField: new Time(13, 50, 0), endTimeField: new Time(15, 30, 0))

        def futureEventCase1 = new EventCase(event: futureEvent1, eventCase: cases)
        def futureEventCase2 = new EventCase(event: futureEvent2, eventCase: cases)
        def eventCaseList = [futureEventCase1, futureEventCase2]

        cases.linkedEvents = eventCaseList
        mockDomain(Case, [cases])
        mockDomain(LegalContact, [])
        mockDomain(EventCase, eventCaseList)
        controller.params.id = cases.caseId

        when:
        def models = controller.show()

        then:
        models["futureEvents"].contains(futureEvent1)
        models["futureEvents"].contains(futureEvent2)
    }
}

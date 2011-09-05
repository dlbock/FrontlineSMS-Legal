package frontlinesms.legal.events

import frontlinesms.legal.*
import java.sql.Time

class EventControllerSpec extends FrontlinesmsLegalControllerSpecBase {

    def "should save event"() {
        setup:
        mockDomain(Event)
        controller.params.eventTitle = "Meeting with someone"
        controller.params.dateFieldSelected = "July 12, 2011"
        controller.params.startTimeField = "08:45AM"
        controller.params.endTimeField = "06:00PM"

        when:
        controller.save()

        then:
        Event.count() == 1
    }

    def "should show error message when date is not specified"() {
        setup:
        mockDomain(Event)
        controller.params.eventTitle = "Meeting with someone"
        controller.params.startTimeField = "08:45AM"
        controller.params.endTimeField = "06:00PM"

        when:
        controller.save()

        then:


        controller.flash.error == 'Please complete all fields.'

    }

    def "should show error message when start time is not specified"() {
        setup:
        mockDomain(Event)
        controller.params.eventTitle = "Meeting with someone"
        controller.params.dateFieldSelected = "July 12, 2011"
        controller.params.endTimeField = "06:00PM"

        when:
        controller.save()

        then:

        controller.flash.error == "Please complete all fields."
    }

    def "should show error message when end time is not specified"() {
        setup:
        mockDomain(Event)
        controller.params.eventTitle = "Meeting with someone"
        controller.params.dateFieldSelected = "July 12, 2011"
        controller.params.startTimeField = "08:45AM"

        when:
        controller.save()

        then:
        controller.flash.error == "Please complete all fields."
    }

    def "should not save event when end time is before start time"() {
        setup:
        mockDomain(Event)
        controller.params.eventTitle = "Event"
        controller.params.dateFieldSelected = "July 12, 2011"
        controller.params.startTimeField = "06:30PM"
        controller.params.endTimeField = "06:30AM"

        when:
        controller.save()

        then:
        Event.count() == 0
    }

    def "should show error message when end time is before start time"() {
        setup:
        mockDomain(Event)
        controller.params.eventTitle = "Event"
        controller.params.dateFieldSelected = "July 12, 2011"
        controller.params.startTimeField = "06:30PM"
        controller.params.endTimeField = "06:30AM"

        when:
        controller.save()

        then:
        controller.flash.error == "End time cannot be before the start time."
    }

    def "should return true for isStartTimeBeforeEndTime if startTime is before endTime"() {
        when:
        controller.params.startTimeField = "06:30AM"
        controller.params.endTimeField = "06:30PM"

        then:
        controller.isStartTimeBeforeEndTime() == true
    }

    def 'should return true if start time  and end time are equal'() {
        when:
        controller.params.startTimeField = "04:30PM"
        controller.params.endTimeField = "04:30PM"

        then:
        controller.isStartTimeBeforeEndTime() == true
    }


    def "should return false for isStartTimeBeforeEndTime if startTime is after endTime"() {
        when:
        controller.params.startTimeField = "06:30PM"
        controller.params.endTimeField = "06:30AM"

        then:
        controller.isStartTimeBeforeEndTime() == false
    }



    def "should save contact when linked on the event"() {
        setup:
        mockDomain(Event)
        mockDomain(EventContact)
        mockCoreClassesToAvoidIssuesWithContactEventHandlers()
        mockDomain(LegalContact,
                [new LegalContact(id: 1, name: "John Doe", primaryMobile: "435352", notes: "hii")])

        when:
        controller.params.eventTitle = "Event"
        controller.params.dateFieldSelected = "July 12, 2011"
        controller.params.startTimeField = "06:30AM"
        controller.params.endTimeField = "07:30AM"
        controller.params.linkedContacts = "1"

        and:
        controller.save()

        then:
        EventContact.count() == 1
    }

    def "should save multiple contacts when linked on the event"() {
        setup:
        mockDomain(Event)
        mockDomain(EventContact)
        mockCoreClassesToAvoidIssuesWithContactEventHandlers()
        mockDomain(LegalContact, [
                new LegalContact(id: 1, name: "John Doe", primaryMobile: "435352", notes: "hii"),
                new LegalContact(id: 34, name: "Jane Do", primaryMobile: "546354", notes: ":)"),
                new LegalContact(id: 87, name: "Jason D", primaryMobile: "57766", notes: "???")
        ])

        when:
        controller.params.eventTitle = "Event"
        controller.params.dateFieldSelected = "July 12, 2011"
        controller.params.startTimeField = "06:30AM"
        controller.params.endTimeField = "07:30AM"
        controller.params.linkedContacts = "1,34,87"

        and:
        controller.save()

        then:
        EventContact.count() == 3
    }

    def "should save case when linked to the event"() {
        setup:
        mockDomain(Event)
        mockDomain(EventCase)
        mockDomain(Case,
                [new Case(caseId: "1112", description: "ertyui")])

        when:
        controller.params.eventTitle = "Event"
        controller.params.dateFieldSelected = "July 12, 2011"
        controller.params.startTimeField = "06:30AM"
        controller.params.endTimeField = "07:30AM"
        controller.params.linkedCases = "1112"

        and:
        controller.save()

        then:

        EventCase.count() == 1
    }

    def "should save multiple cases when linked to the event"() {
            setup:
            mockDomain(Event)
            mockDomain(EventCase)
            mockDomain(Case, [
                    new Case(caseId: "1112", description: "ertyui"),
                    new Case(caseId: "1113", description: "ertydfgsui"),
                    new Case(caseId: "1114", description: "edfgrtyui")
            ])

            when:
            controller.params.eventTitle = "Event"
            controller.params.dateFieldSelected = "July 12, 2011"
            controller.params.startTimeField = "06:30AM"
            controller.params.endTimeField = "07:30AM"
            controller.params.linkedCases = "1112,1113,1114"

            and:
            controller.save()

            then:
            EventCase.count() == 3
        }

    def 'should update event when title, date, start date, end date are given'() {
        given:
        def date = new Date("August 26,2011")
        def startTime = Time.valueOf("08:45:00")
        def endTime = Time.valueOf("11:45:00")

        def theEvent = new Event(eventTitle: "test", dateFieldSelected: date, startTimeField: startTime, endTimeField: endTime)
        def events = [theEvent]
        mockDomain(Event, events)

        controller.params.eventId = theEvent.id
        controller.params.eventTitle = "update-test"
        controller.params.dateFieldSelected = "August 26,2011"
        controller.params.startTimeField = "02:39AM"
        controller.params.endTimeField = "03:39AM"


        when:
        controller.update()

        then:
        def updatedEvent = Event.findByEventTitle("update-test")
        updatedEvent.eventTitle.equals("update-test")
        Event.findByEventTitle("test") == null
    }

    def 'should link contact to event when the event is updated'() {
        setup:
        def date = new Date("August 26,2011")
        def startTime = Time.valueOf("08:45:00")
        def endTime = Time.valueOf("11:45:00")
        def event = new Event(eventTitle: "test", dateFieldSelected: date, startTimeField: startTime, endTimeField: endTime)
        mockDomain(Event, [event])
        mockDomain(EventContact)
        mockCoreClassesToAvoidIssuesWithContactEventHandlers()
        mockDomain(LegalContact,
                [new LegalContact(id: 1, name: "John Doe", primaryMobile: "435352", notes: "hii"),
                 new LegalContact(id: 2, name: "Jane Smith", primaryMobile: "12345", notes: "hii")])

        controller.params.eventId = event.id
        controller.params.eventTitle = "test"
        controller.params.dateFieldSelected = "August 26,2011"
        controller.params.startTimeField = "08:45:00"
        controller.params.endTimeField = "11:45:00"
        controller.params.linkedContacts = "1,2"

        when:
        controller.update()

        then:
        EventContact.count() == 2
        event.linkedContacts.size() == 2
    }
}


package frontlinesms.legal.schedule

import frontlinesms.legal.Event
import frontlinesms.legal.EventContact
import frontlinesms.legal.LegalContact
import frontlinesms2.Contact
import grails.plugin.spock.ControllerSpec
import frontlinesms.legal.Case

class ScheduleControllerSpec extends ControllerSpec {
    def "delete event with event id"() {
        given:
        def theEvent = new Event(eventTitle: "test")
        def events = [theEvent]
        mockDomain(Event, events)

        when:
        controller.params.id = theEvent.id;
        controller.deleteEvent()

        then:
        Event.count() == 0
        controller.response.outputStream.targetStream.toString() == 'Event is successfully deleted'
    }

    def 'send error message on delete action if event not found'() {
        given:
        mockDomain(Event, [])

        when:
        controller.params.id = 1
        controller.deleteEvent()

        then:
        controller.response.outputStream.targetStream.toString() == 'Event not found!!!'
    }

    def 'should populate contactList when the Schedule Page is loaded'() {
        given:
        def theEvent = new Event(eventTitle: "test")
        mockDomain(Event, [theEvent])
        def newContact = [new Contact(), new Contact(), new Contact()]
        mockDomain(Contact, newContact)
        mockDomain(Case)

        controller.params.id = theEvent.id;

        when:
        def models = controller.index()

        then:
        models['contactList'] == newContact
    }
}

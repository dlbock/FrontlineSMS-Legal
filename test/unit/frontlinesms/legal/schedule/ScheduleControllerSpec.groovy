package frontlinesms.legal.schedule

import frontlinesms.legal.Event
import frontlinesms.legal.LegalContact
import grails.plugin.spock.ControllerSpec
import frontlinesms2.Contact
import frontlinesms.legal.EventContact

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
        def events = [theEvent]
        mockDomain(Event, events)
        controller.params.id = theEvent.id;
        def newContact = [new Contact(), new Contact(), new Contact()]
        mockDomain(Contact, newContact)

        when:
        def models = controller.index()

        then:
        models['contactList'] == newContact
    }

    def 'unlink contact from event'(){
        given:
        def contact = new LegalContact(id: 42)
        def otherContact = new LegalContact()
        def event = new Event(id: 1)
        def eventContact = new EventContact(event: event, legalContact: contact)
        def otherEventContact = new EventContact(event: event, legalContact: otherContact)
        mockDomain(LegalContact, [contact, otherContact])
        mockDomain(Event, [event])
        mockDomain(EventContact, [eventContact, otherEventContact])

        when:
        controller.params.eventId = "1"
        controller.params.contactId = "42"
        controller.unlinkContact()

        then:
        def links = EventContact.findAll()
        links.size() == 1 && !links.contains(eventContact)
    }

    def 'send success message when unlinking contact from event'() {
        given:
        mockDomain(Event)
        mockDomain(LegalContact)
        mockDomain(EventContact, [new EventContact()])

        when:
        controller.unlinkContact()

        then:
        controller.response.contentAsString == "successfully unlinked"
    }
}


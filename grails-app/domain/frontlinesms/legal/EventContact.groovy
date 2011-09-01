package frontlinesms.legal

class EventContact {
    static mapping = {
        table 'event_contact_links'
    }

    static belongsTo = [event :Event]

    LegalContact legalContact
    Event event

    static EventContact link(event, legalContact) {
        def contactEventLink = EventContact.findByEventAndLegalContact(event, legalContact)
        if (!contactEventLink) {
            contactEventLink = new EventContact()
            legalContact?.addToLinkedEvents(contactEventLink)
            event?.addToLinkedContacts(contactEventLink)
            contactEventLink.save()
            legalContact.save(flush: true)
            event.save(flush: true)
        }
        return contactEventLink
    }

    static LegalContact[] findContactsByEvent(Event event) {
        def linkedContacts = EventContact.findAllByEvent(event)
        linkedContacts.collect { it -> it.legalContact}
    }

    static Event[] findEventsByContact(LegalContact legalContact) {
        def linkedEventContacts = EventContact.findAllByLegalContact(legalContact)
        linkedEventContacts.collect {it -> it.event}
    }

    static void unlink(legalContact) {
        def allEvent = frontlinesms.legal.EventContact.findEventsByContact(legalContact)
         if (allEvent) {
            for(oneEvent in allEvent){
            EventContact.findByEventAndLegalContact(oneEvent, legalContact).delete()
            }
         }
    }
}

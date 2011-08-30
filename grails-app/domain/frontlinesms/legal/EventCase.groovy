package frontlinesms.legal

class EventCase {
    static mapping = {
        table 'event_case_links'
    }

    static belongsTo = [event :Event]

    Case eventCase
    Event event

    static EventCase link(event, eventCase) {
        def caseEventLink = EventCase.findByEventAndEventCase(event, eventCase)
        if (!caseEventLink) {
            caseEventLink = new EventCase()
            eventCase?.addToLinkedEvents(caseEventLink)
            event?.addToLinkedCases(caseEventLink)
            caseEventLink.save()
            eventCase.save(flush: true)
            event.save(flush: true)
        }
        return caseEventLink
    }

    static Case[] findCasesByEvent(Event event) {
        def linkedCases = EventCase.findAllByEvent(event)
        linkedCases.collect { it -> it.eventCase}
    }
}

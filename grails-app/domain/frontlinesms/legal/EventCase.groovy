package frontlinesms.legal

class EventCase {
    static mapping = {
        table 'event_case_links'
    }

    static belongsTo = [event :Event]

    Case eventCase
    Event event

}

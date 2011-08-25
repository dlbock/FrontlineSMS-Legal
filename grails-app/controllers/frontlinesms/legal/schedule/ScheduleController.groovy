package frontlinesms.legal.schedule

import frontlinesms.legal.Event
import frontlinesms.legal.EventContact
import frontlinesms.legal.LegalContact

class ScheduleController {

    def index = {
    [hh:"2011, 5"]
    [contactList: LegalContact.list()]
    }

    def fetchEvents = {
        def eventsList = Event.list()
        render(contentType: "text/json") {
            array {
                for (e in eventsList) {
                    def startTime = new Date(e.dateFieldSelected.year, e.dateFieldSelected.month, e.dateFieldSelected.date, e.startTimeField.hours, e.startTimeField.minutes)
                    def endTime = new Date(e.dateFieldSelected.year, e.dateFieldSelected.month, e.dateFieldSelected.date, e.endTimeField.hours, e.endTimeField.minutes)
                    event(id: e.id, title: e.eventTitle, start: startTime, end: endTime)
                }
            }
        }

    }

        def fetchEventContacts = {
        if (params.id != null && params.id != "") {
            def linkedContacts = EventContact.findContactsByEvent(Event.findById(params.id))
            render(contentType: "text/json") {
                array {
                    for (c in linkedContacts) {
                        event(id: c.id, name: c.name, primaryMobile: c.primaryMobile, notes: c.notes)
                    }
                }
            }
        } else {
            render(contentType: "text/json") {
                array {
                }
            }
        }
    }

    def deleteEvent = {
        def event = Event.get(params.id);
        if (event != null) {
            event.delete();
            render "Event is successfully deleted"
        }
        else
            render "Event not found!!!"
    }

    def unlinkContact = {
        def event = Event.get(params.eventId as Integer)
        def contact = LegalContact.findByName(params.contactName )
        def eventContact = EventContact.findByEventAndLegalContact(event, contact)
        eventContact.delete()
        render "successfully unlinked"
    }
}

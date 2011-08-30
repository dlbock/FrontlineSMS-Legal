package frontlinesms.legal.schedule

import frontlinesms.legal.Event
import frontlinesms.legal.EventCase
import frontlinesms.legal.EventContact
import frontlinesms.legal.LegalContact
import java.sql.Time
import frontlinesms.legal.TimeFormatter
import org.openqa.selenium.Alert

class ScheduleController {

    def index = {
        [hh: "2011, 5"]
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

    def fetchEventCases = {
        if (params.id != null && params.id != "") {
            def linkedCases = EventCase.findCasesByEvent(Event.findById(params.id))
            render(contentType: "text/json") {
                array {
                    for (c in linkedCases) {
                        event(id: c.caseId, status: c.active)
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
        def contact = LegalContact.findByName(params.contactName)
        def eventContact = EventContact.findByEventAndLegalContact(event, contact)
        eventContact.delete()
        render "successfully unlinked"
    }

    def updateEvent = {
        def formattedParams = formatParameters()
        def event = Event.findById(params.eventId)
        event.eventTitle = formattedParams.eventTitle
        event.dateFieldSelected = new Date(params.eventDate)
        event.startTimeField = Time.valueOf(formattedParams.startTimeField)
        event.endTimeField = Time.valueOf(formattedParams.endTimeField)
        event.save(flush: true)
    }

    private def formatParameters() {
        [
                startTimeField: TimeFormatter.formatTime(params.eventStartTime),
                endTimeField: TimeFormatter.formatTime(params.eventEndTime),
                eventTitle: (params.eventTitle == null || params.eventTitle.trim() == "") ? "Untitled Event" : params.eventTitle.trim()
        ]
    }
}

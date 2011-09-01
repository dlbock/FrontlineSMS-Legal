package frontlinesms.legal.events

import java.sql.Time
import java.text.SimpleDateFormat
import frontlinesms.legal.*

class EventController {

    def index = { }
    def create = {
        params.eventTitle = params.eventTitle ? params.eventTitle : ""
        params.startTimeField = params.startTimeField ? params.startTimeField : ""
        params.dateFieldSelected = params.dateFieldSelected ? params.dateFieldSelected : ""
        params.endTimeField = params.endTimeField ? params.endTimeField : ""
        [allCases: Case.getAll(),contactList: LegalContact.list()]
    }


    def save = {
        if (checkForNullDateTimes()) {

            flash.error = "Please complete date and time fields."

            redirect(action: "create", params: [eventTitle: params.eventTitle, dateFieldSelected: params.dateFieldSelected, startTimeField: params.startTimeField, endTimeField: params.endTimeField])

        }
        else {
            def formattedParams = formatParameters()

            if (isStartTimeBeforeEndTime()) {
                def YearFormat =new SimpleDateFormat("yyyy");
                Date selectedDate= new Date(params.dateFieldSelected);
                def newEvent = new Event(eventTitle: formattedParams.eventTitle, dateFieldSelected: new Date(params.dateFieldSelected), startTimeField: Time.valueOf(formattedParams.startTimeField), endTimeField: Time.valueOf(formattedParams.endTimeField))
                if (newEvent.save(flush: true)) {
                    linkContactsToEvent(newEvent)
                    linkCasesToEvent(newEvent)
                    flash.message = "Event created."
                    chain(controller: "schedule", action: "index", model:[year:YearFormat.format(selectedDate),month:selectedDate.month])
                }
                else {
                    flash.error = "There was a problem saving your event."

                    redirect(action: "create", params: [eventTitle: params.eventTitle, dateFieldSelected: params.dateFieldSelected, startTimeField: params.startTimeField, endTimeField: params.endTimeField])
                }
            }
            else {
                flash.error = "End time cannot be before the start time."
                redirect(action: "create", params: [eventTitle: params.eventTitle, dateFieldSelected: params.dateFieldSelected, startTimeField: params.startTimeField, endTimeField: params.endTimeField])
            }


        }
    }

    def update = {
        println(params)
        def formattedParams = formatParameters()
        def event = Event.findById(params.eventId)
        event.eventTitle = formattedParams.eventTitle
        event.dateFieldSelected = new Date(params.dateFieldSelected)
        event.startTimeField = Time.valueOf(formattedParams.startTimeField)
        event.endTimeField = Time.valueOf(formattedParams.endTimeField)
        event.save(flush: true)
    }

    private def linkContactsToEvent(event) {
        if (params.linkedContacts != null && params.linkedContacts != "") {
            def contactIds = params.linkedContacts.split(",")
            contactIds.each { it ->
                def contact = LegalContact.findById(it as Integer)
                EventContact.link(event, contact)
            }
        }
    }

    private def formatParameters() {
        [
                startTimeField: TimeFormatter.formatTime(params.startTimeField),
                endTimeField: TimeFormatter.formatTime(params.endTimeField),
                eventTitle: (params.eventTitle == null || params.eventTitle.trim() == "") ? "Untitled Event" : params.eventTitle.trim()
        ]
    }

    private def isStartTimeBeforeEndTime() {
        Time start = Time.valueOf(TimeFormatter.formatTime(params.startTimeField))
        Time end = Time.valueOf(TimeFormatter.formatTime(params.endTimeField))
        if (start.equals(end))
            return true
        return start.before(end)
    }


    private def checkForNullDateTimes() {
        return (params.dateFieldSelected == null || params.startTimeField == null || params.endTimeField == null || params.dateFieldSelected == "" || params.startTimeField == "" || params.endTimeField == "")
    }

    private def linkCasesToEvent(event) {
        if (params.linkedCases != null && params.linkedCases != "") {
            def caseIds = params.linkedCases.split(",")
            caseIds.each { it ->
                def eventCase = Case.findByCaseId(it as String)
                EventCase.link(event, eventCase)
            }
        }
    }
}

package frontlinesms.legal.functionaltests.pages.schedule

import geb.Module
import geb.Page
import frontlinesms.legal.functionaltests.pages.ConfirmationDialog

class SchedulePage extends Page {
    static at = { $("title").text() == "Schedule" }
    static url = "schedule"
    static content = {
        events {
            $("span[class='fc-event-title']")
        }
        eventContacts {
                $("tr[class='event-contact']")
        }
        eventListSize {
            $("span[class='fc-event-title']").size()
        }

        deleteEvent {
            $('#delete-event').click()
            unlinkConfirmationDialog.confirm()
            true
        }

        atDate {$('span.fc-header-title').text()}

        eventDialog(wait: true) { module EventDialog }
        unlinkConfirmationDialog(required: false) { module ConfirmationDialog, messageId: "contactUnlinkDialog", buttonIdPrefix: "cancel-" }
    }
}

class EventDialog extends Module {
    static base = { $(id: "view-event") }
    static content = {
        
        contactsLinkedToEvent {
            $(".event-contact").collect {module ContactRow, it}
        }
        eventTitle { $('#event-title').text()}
        eventDate { $('#event-date').text()}
        eventStartTime { $('#event-start-time').text()}
        eventEndTime { $('#event-end-time').text()}
        contactsLinkedToEvent { $(".event-contact").collect {module ContactRow, it} }
        atDate{$('span.fc-header-title').text()}
        contactUnlinkDialog { $("div", id: "contactUnlinkDialog") }
        contactUnlinkYes { $("button", id: "confirm-yes")}
        contactUnlinkNo { $("button", id: "confirm-no")}

    }
}

class ContactRow extends Module {
    static content = {
        cell { i -> $("td", i) }
        name { cell(0).text() }
        primaryMobileNumber { cell(1).text() }
        unlinkContact { $("a", class: "unlink-contact")}
    }
}

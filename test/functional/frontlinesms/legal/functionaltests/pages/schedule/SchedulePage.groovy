package frontlinesms.legal.functionaltests.pages.schedule

import geb.Module
import geb.Page
import frontlinesms.legal.functionaltests.pages.ConfirmationDialog

class SchedulePage extends Page {
    static at = { $("title").text() == "Schedule" }
    static url = "schedule"
    static content = {
        events {
            try {
                $("span[class='fc-event-title']")
            }
            catch (Exception e) {
                null
            }
        }
        testEvent { $("span[class='fc-event-title']", text: "test event") }
        eventContacts {
            try {
                $("tr[class='event-contact']")

            }
            catch (Exception e) {
                null
            }
        }
        eventListSize {
            try {
                $("span[class='fc-event-title']").size()

            }
            catch (Exception e) {
                0
            }
        }
        deleteEvent {
            $('#delete-event').click()
            unlinkConfirmationDialog.confirm()
            true
        }

        atDate {$('span.fc-header-title').text()}

        eventDialog(wait: true) { module EventDialog }
        unlinkConfirmationDialog(required: false) { module ConfirmationDialog, messageId: "contactUnlinkDialog" }
    }
}

class EventDialog extends Module {
    static base = { $(id: "view-event") }
    static content = {
        eventTitle { $('#event-title').text()}
        eventDate { $('#event-date').text()}
        eventStartTime { $('#event-start-time').text()}
        eventEndTime { $('#event-end-time').text()}
        contactsLinkedToEvent { $(".event-contact").collect {module ContactRow, it} }
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

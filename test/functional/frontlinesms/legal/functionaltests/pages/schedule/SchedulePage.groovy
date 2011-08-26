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
        deleteEventButton { $('#delete-event') }
        deleteEvent {
            deleteEventButton.click()
            deleteConfirmationDialog.confirm()
            true
        }
        linkContactToExistingEvent {
            $('#link-contact-to-existing-event-button').click()
            true
        }
        atDate {$('span.fc-header-title').text()}
        existingContactList {
            $("#contactsTable tbody tr").collect {module LinkContactRow, it}
        }
        eventDialog(wait: true) { module EventDialog }
        unlinkConfirmationDialog(required: false) { module ConfirmationDialog, messageId: "contactUnlinkDialog" }
        deleteConfirmationDialog(required: false) { module ConfirmationDialog, messageId: "delete-event-dialog" }
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
        atDate{$('span.fc-header-title').text()}
    }
}

class ContactRow extends Module {
    static content = {
        cell { i -> $("td", i) }
        name { cell(0).text() }
        primaryMobileNumber { cell(1).text() }
        unlinkContact { $("a", class: "unlink-contact")}
        linkContactToExistingEventDialog{$("div", id: "link-contact-to-existing-event-dialog")}
    }
}

class LinkContactRow extends Module {
    static content = {
        cell { i -> $("td", i) }
        contactName { cell(0).text() }
        contactNumber { cell(1).text() }
        linkContact {$("a", class:"link")}
    }
}

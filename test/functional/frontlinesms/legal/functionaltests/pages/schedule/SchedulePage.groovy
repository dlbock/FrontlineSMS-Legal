package frontlinesms.legal.functionaltests.pages.schedule

import frontlinesms.legal.functionaltests.pages.ConfirmationDialog
import geb.Module
import geb.Page

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
            $('#link-contact-button').click()
            true
        }
        atDate {$('span.fc-header-title').text()}

        existingContactList {
            $("#contactsTable tbody tr").collect {module LinkContactRow, it}
        }

        dateFieldSelected {
            $("#event-date").click()
            waitFor(3) { datePicker.present }
        }
        datePicker { $("div", id: "ui-datepicker-div") }

        eventDialog(wait: true) { module EventDialog }
        unlinkConfirmationDialog(required: false) { module ConfirmationDialog, messageId: "unlink-contact-dialog" }
        deleteConfirmationDialog(required: false) { module ConfirmationDialog, messageId: "delete-event-dialog" }
        linkContactFromPopup {
            $('.contactLink')[0].click()
            true
        }
        linkedContactsInEventDetailsPopup {
            $("#event-contacts-table tbody tr").collect {module LinkContactRow, it}
        }
        CancelButtonIsClicked {
            $("#cancel-button").click()
            true
        }
        contactNameSearch {
            $("input", id: "contact-name-search")
        }
        contactLinkNotVisible { $("tr", class: "contactRow", filtermatch: "false").collect {module LinkContactRow, it} }

        updateEvent { $("input", id: "update-event") }
        errorMessage { $("div", id: "error-message").text()}
        eventStartTime {$("input", id: "event-start-time")}
        eventEndTime {$("input", id: "event-end-time")}
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
        atDate {$('span.fc-header-title').text()}
    }
}

class ContactRow extends Module {
    static content = {
        cell { i -> $("td", i) }
        name { cell(0).text() }
        primaryMobileNumber { cell(1).text() }
        unlinkContact { $("a", class: "unlink-contact")}
        linkContactToExistingEventDialog {$("div", id: "link-contact-dialog")}
    }
}

class LinkContactRow extends Module {
    static content = {
        cell { i -> $("td", i) }
        contactName { cell(0).text() }
        contactNumber { cell(1).text() }
        linkContact {$("a", class: "link")}
    }
}

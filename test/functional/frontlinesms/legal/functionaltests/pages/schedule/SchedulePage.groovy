package frontlinesms.legal.functionaltests.pages.schedule

import frontlinesms.legal.functionaltests.pages.ConfirmationDialog
import geb.Module
import geb.Page
import frontlinesms.legal.functionaltests.pages.DateField

class SchedulePage extends Page {
    static at = { $("title").text() == "Schedule" }
    static url = "schedule"
    static content = {
        calendarHeader { $('.fc-header-title').text() }
        events { $('.fc-event-title') }

        eventDialog(wait: true) { module EventDialog }
        unlinkConfirmationDialog(required: false) { module ConfirmationDialog, messageId: "unlink-contact-dialog" }
        deleteConfirmationDialog(required: false) { module ConfirmationDialog, messageId: "delete-event-dialog" }

        existingContactList {
            $("#contactsTable tbody tr").collect {module LinkContactRow, it}
        }

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
        contactsNotInSearchResults { $("tr", class: "contactRow", filtermatch: "false").collect {module LinkContactRow, it} }

        linkContactSearchDialog {$('#link-contact-dialog')}
        yesConfirmationButton { $("#confirm-yes")}
        noConfirmationButton { $("#confirm-no")}
        confirmationDialog { $("#confirmation-dialog")}
    }
}

class EventDialog extends Module {
    static base = { $(id: "view-event") }
    static content = {
        title { $('#event-title') }
        date { module DateField, $('#event-date') }
        startTime { $('#event-start-time') }
        endTime { $('#event-end-time') }
        contactsLinkedToEvent { $(".event-contact").collect {module ContactRow, it} }
        updateEventButton { $('#update-event') }
        deleteEventButton { $('#delete-event') }
        errorMessage { $(id: "error-message").text() }

        updateEvent {
            updateEventButton.click()
            waitFor { try { !$().isVisible() } catch(e) { true } }
            waitFor { browser.at SchedulePage }
        }

        deleteEvent {
            deleteEventButton.click()
            page.deleteConfirmationDialog.confirm()
            true
        }

        linkContact {
            $('#link-contact-button').click()
            true
        }
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

class LinkContactRow extends Module {
    static content = {
        cell { i -> $("td", i) }
        contactName { cell(0).text() }
        contactNumber { cell(1).text() }
        linkContact {$("a", class: "link")}
    }
}

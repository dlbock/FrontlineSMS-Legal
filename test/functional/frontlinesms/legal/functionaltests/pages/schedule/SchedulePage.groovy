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
        existingContactList {
            $("#contactsTable tbody tr").collect {module LinkContactRow, it}
        }
        events {
            $("span[class='fc-event-title']")
        }
        selectTestEvent {
            events.find { it.text() == "test event" }.click()
            true
        }
        atDate {$('span.fc-header-title').text()}
        existingContactList {
            $("#contactsTable tbody tr").collect {module LinkContactRow, it}
        }
        eventDialog(wait: true) { module EventDialog }
        unlinkConfirmationDialog(required: false) { module ConfirmationDialog, messageId: "unlink-contact-dialog" }
        deleteConfirmationDialog(required: false) { module ConfirmationDialog, messageId: "delete-event-dialog" }
        linkContactFromPopup {
            $('.contactLink')[0].click()
            true
        }
        unlinkFirstContactLinkedToEvent {
            eventDialog.unlinkFirstContactLinkedToEvent()
        }
        noLinkedContactsToEvent { eventDialog.contactsLinkedToEvent.size() == 0}
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

        linkCaseButton {  $("#link-case-button")}
        linkCaseButtonExists { $("#link-case-button").size() == 1}
        linkCaseDialog { $("div", id: "link-case-dialog") }
        caseDialogCancelButton { $("#cancel-link-case") }
        casesToLink { $("tr", class: "caseLink").collect {module LinkableCase, it} }
        existingCaseList {
            $("#cases tbody tr").collect {module LinkableCase, it}
        }
        linkToBeSelected{ $(".link-case")}
    }
}

class EventDialog extends Module {
    static base = { $(id: "view-event") }
    static content = {
        title { $('#event-title') }
        close {
            $(".ui-dialog-titlebar-close ui-corner-all") .click()
            true
        }
        confirmYesOnCloseWithoutUpdating {
            $("#confirm-yes").click()
            true
        }
        contactsLinkedToEvent {
            $(".event-contact").collect {module ContactRow, it}
        }
        date { module DateField, $('#event-date') }
        startTime { $('#event-start-time') }
        endTime { $('#event-end-time') }
        contactsLinkedToEvent { $(".event-contact").collect {module ContactRow, it} }
        updateEventButton { $('#update-event') }
        deleteEventButton { $('#delete-event') }
        errorMessage { $(id: "error-message").text() }
        unlinkFirstContactLinkedToEvent() {
            contactsLinkedToEvent[0].unlinkContact.click()
            true
        }

        updateEvent() {
            updateEventButton.click()
            waitFor { try { !$().isVisible() } catch(e) { true } }
            waitFor { browser.at SchedulePage }
        }

        deleteEvent {
            deleteEventButton.click()
            page.deleteConfirmationDialog.confirm()
            true
        }

        clickLinkContact {
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

class LinkableCase extends Module {
    static content = {
        cell { i -> $("td", i) }
        name { $(class: "case-name").text() }
        number { cell(1).text() }
        linkCase { cell(2) }
    }
}


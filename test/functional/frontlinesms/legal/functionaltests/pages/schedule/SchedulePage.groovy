package frontlinesms.legal.functionaltests.pages.schedule

import frontlinesms.legal.functionaltests.pages.ConfirmationDialog
import frontlinesms.legal.functionaltests.pages.DateField
import geb.Module
import geb.Page

class SchedulePage extends Page {
    static at = { $("title").text() == "Schedule" }
    static url = "schedule"
    static content = {
        calendarHeader { $('.fc-header-title').text() }
        events { $("span[class='fc-event-title']") }
        eventDialog(wait: true) { module EventDialog }
        unlinkConfirmationDialog(required: false) { module ConfirmationDialog, messageId: "unlink-contact-dialog" }
        deleteConfirmationDialog(required: false) { module ConfirmationDialog, messageId: "delete-event-dialog" }

        selectEvent { title ->
            events.find { it.text() == title }.click()
            true
        }

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
        linkContactDialog { module LinkContactDialog, page.$() }

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

class LinkContactDialog extends Module {
    static base = { $('#link-contact-dialog').parent(".ui-dialog") }
    static content = {
        searchbox { $(id: "contact-name-search") }
        contacts { $("#contactsTable tbody tr").findAll { it.displayed }.collect { module LinkContactRow, it } }

        searchFor { query ->
            searchbox.value(query)
            sleep(500)
            true
        }

        link { name ->
            contacts.find{ it.contactName == name }.link()
            true
        }

        cancel {
            $("#cancel-button").click()
            true
        }
    }
}

class LinkContactRow extends Module {
    static content = {
        cell { i -> $("td", i) }
        contactName { cell(0).text() }
        contactNumber { cell(1).text() }

        link {
            $("a").find { it.text() == "Link" }.click()
            true
        }
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


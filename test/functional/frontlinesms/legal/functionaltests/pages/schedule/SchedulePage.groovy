package frontlinesms.legal.functionaltests.pages.schedule

import frontlinesms.legal.functionaltests.pages.ConfirmationDialog
import frontlinesms.legal.functionaltests.pages.DateField
import geb.Module
import geb.Page
import frontlinesms.legal.functionaltests.pages.LinkableCase
import frontlinesms.legal.functionaltests.pages.LinkedCaseRow

class SchedulePage extends Page {
    static at = { $("title").text() == "Schedule" }
    static url = "schedule"
    static content = {
        calendarHeader { $('.fc-header-title').text() }
        events { $("span[class='fc-event-title']") }
        eventDialog(wait: true) { module EventDialog }
        unlinkConfirmationDialog(required: false) { module ConfirmationDialog, messageId: "unlink-contact-dialog" }
        deleteConfirmationDialog(required: false) { module ConfirmationDialog, messageId: "delete-event-dialog" }
        cancelEditConfirmationDialog { module ConfirmationDialog, messageId: "confirmation-dialog" }

        selectEvent { title ->
            events.find { it.text() == title }.click()
            true
        }

        casesToLink { $("tr", class: "caseLink").collect {module LinkableCase, it} }
        existingCaseList {
            $("#cases tbody tr").collect {module LinkedCaseRow, it}
        }
        existingContactList {
            $("#event-contacts-table tbody tr").collect {module ContactRow, it}
        }
        linkToBeSelected{ $(".link-case")}
    }
}

class EventDialog extends Module {
    static base = { $(id: "view-event") }
    static content = {
        title { $('#event-title') }
        linkContactDialog { module LinkContactDialog, page.$() }
        linkCaseDialog { module LinkCaseDialog, page.$() }

        linkCase {
            $("#link-case-button").click()
            true
        }

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

class LinkCaseDialog extends Module {
    static base = { $('#link-case-dialog').parent(".ui-dialog") }
    static content = {
        cancel {
            $("#cancel-link-case").click()
            true
        }
    }
}



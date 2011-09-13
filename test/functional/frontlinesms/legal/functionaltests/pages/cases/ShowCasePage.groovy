package frontlinesms.legal.functionaltests.pages.cases

import geb.Page
import geb.Module
import frontlinesms.legal.functionaltests.pages.LinkContactDialog

class ShowCasePage extends Page {
    static at = { $("title").text() == "ShowCasePage"}
    static url = "case/show"
    static content = {
        caseId { $("input", id: "case-id").value() }
        currentId {$("input", id: "current-Id").value()}
        description { $("textarea", id: "case-description").value()}
        updateCaseButton {$("input", id: "case-update")}
        deleteButton {$("button", id: "delete-button")}
        caseActive { $("input", id: "case-status") }
        linkContact {
            $("button", id: "link-contact-button").click()
            true
        }
        statusMessage { $("div", id: "status").text() }
        errorMessage { $("div", id: "errorMessage").text() }
        unlinkButton {$(".unlink-contact-button")}
        deleteDialog { $("div", id: "deleteDialog") }
        deleteYes { $("button", id: "confirm-yes")}
        deleteNo { $("button", id: "confirm-no")}
        linkContactDialog { module LinkContactDialog }
        cancelDialog { $("div", id: "case-update-cancel-dialog") }
        cancelButton {$("button", id: "case-update-cancel")}
        cancelYes { $("button", id: "cancel-confirm-yes")}
        cancelNo { $("button", id: "cancel-confirm-no")}
        pastEventsTable {$("#past-events tbody tr").collect {module EventRow, it} }
        futureEventsTable {$("#future-events tbody tr").collect {module EventRow, it} }
        currentEventsTable {$("#current-events tbody tr").collect {module EventRow, it} }
        relationshipCancelButton { $("button", id: "cancel-relationship")}
        relationshipConfirmButton { $("button", id: "confirm-relationship")}
        sizeOflinkedContactsTable {$("#contacts tbody tr:not(:first-child)").size()}
        linkedContactsTable {$("#contacts tbody tr:not(:first-child)").collect {module ContactRow, it}}
    }
}

class ContactRow extends Module {
    static content = {
        cell {i -> $("td", i)}
        name { cell(0).text() }
        primaryMobile { cell(1).text() }
        link {
            $("a").find { it.text() == "Link" }.click()
            true
        }
    }
}

class EventRow extends Module {
    static content = {
        cell {i -> $("td", i)}
        title { cell(0).text() }
    }
}
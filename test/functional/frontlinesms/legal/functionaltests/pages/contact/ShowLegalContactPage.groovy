package frontlinesms.legal.functionaltests.pages.contact

import geb.Module
import geb.Page
import frontlinesms.legal.functionaltests.pages.LinkedCaseRow
import frontlinesms.legal.functionaltests.pages.LinkCaseDialog

class ShowLegalContactPage extends Page {
    static at = { $("title").text() == "Show Contact Page"}
    static url = "legalContact/show"
    static content = {
        name { $("input", id: "contact-name").value() }
        primaryMobile { $("input", id: "contact-primary-mobile").value()}
        notes {$("textarea", id: "contact-notes")}
        linkCaseButton { $("button", id: "link-case-button")}
        linkCaseDialog {module LinkCaseDialog}
        linkedCasesTable {
            $("#cases tbody tr").collect {module LinkedCaseRow, it}
        }
        pastEventsTable {$("#past-events tbody tr").collect {module EventRow, it} }
        futureEventsTable {$("#future-events tbody tr").collect {module EventRow, it} }
        deleteButton {$("input", id: "delete-button")}
        deleteDialog { $("div", id: "deleteDialog") }
        deleteYes { $("button", id: "confirm-yes")}
        deleteNo { $("button", id: "confirm-no")}
        currentEventsTable {$("#current-events tbody tr").collect {module EventRow, it} }
        updateContactButtonIsDisabled { $("#contact-save:disabled").size() }
        caseContactRelationshipDialog { $("div", id: "case-contact-relationship-dialog")}
        relationshipInput { $("input", id: "case-contact-relationship")}
        relationshipConfirmButton { $("button", id: "confirm-relationship")}
        relationshipCancelButton { $("button", id: "cancel-relationship")}
        updateButton {$("#contact-save")}
    }
}

class EventRow extends Module {
    static content = {
        cell { i -> $("td", i) }
        title { cell(0).text() }
    }
}
package frontlinesms.legal.functionaltests.pages.cases

import geb.Page
import geb.Module
import frontlinesms.legal.functionaltests.pages.ConfirmationDialog

class ShowCasePage extends Page {
    static at = { $("title").text() == "ShowCasePage"}
    static url = "case/show"
    static content = {
        caseId { $("input", id: "case-id").value() }
        currentId {$("input", id: "current-Id").value()}
        description { $("textarea", id: "case-description").value()}
        updateCaseButton {$("input", id: "case-update")}
        deleteButton{$("button", id:"delete-button")}
        caseActive { $("input", id: "case-status") }
        clickLinkContact {$("#link-contact-button")}
        contactsTable {$("#link-contacts-inner-table-div #contactsTable tbody tr").collect {module ContactRow, it} }
        statusMessage { $("div", id: "status").text() }
        errorMessage { $("div", id: "errorMessage").text() }
        linkedContactsRow {$(name:"contactRow", id: "contact-row")}
        deleteDialog { $("div", id: "deleteDialog") }
        deleteYes { $("button", id: "confirm-yes")}
        deleteNo { $("button", id: "confirm-no")}
    }
}

class ContactRow extends Module {
    static content = {
        cell { i -> $("td", i) }
        name { cell (0).text() }
        primaryMobile { cell(1).text() }
    }
}

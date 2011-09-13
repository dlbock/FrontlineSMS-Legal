package frontlinesms.legal.functionaltests.pages.cases

import geb.Page
import frontlinesms.legal.functionaltests.pages.LinkContactDialog

class NewCasePage extends Page {
    static at = { $("title").text() == "Create New Case" }
    static url = "case/create"
    static content = {
        caseId { $("input", id: "case-id") }
        description { $("input", id: "case-description") }
        save { $("button", id: "case-save") }
        cancel { $("button", id: "case-cancel") }
        errorMessage { $("div", id: "errorMessage").text() }
        caseCancelDialog { $("div", id: "case-cancel-dialog")}
        cancelYes { $("button", id: "cancel-confirm-yes")}
        cancelNo { $("button", id: "cancel-confirm-no")}
        linkContact {
            $("button", id: "link-contact-button").click()
            true
        }
        linkContactDialog { module LinkContactDialog }
        caseTitle {$("input", id: "case-title")}
        saveWithoutCaseTitleYes { $("button", id: "save-confirm-yes")}
        saveWithoutCaseTitleNo { $("button", id: "save-confirm-no")}
        relationshipConfirmButton { $("button", id: "confirm-relationship")}
        relationshipCancelButton { $("button", id: "cancel-relationship")}
        sizeOflinkedContactsTable {$("#contacts tbody tr:not(:first-child)").size()}
    }
}
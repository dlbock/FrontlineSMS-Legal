package frontlinesms.legal.functionaltests.pages.contact

import geb.Page

import frontlinesms.legal.functionaltests.pages.LinkedCaseRow
import frontlinesms.legal.functionaltests.pages.LinkCaseDialog

class CreateLegalContactPage extends Page {
    static at = { $("title").text() == "Create New Contact" }
    static url = "legalContact/create"
    static content = {
        name { $("input", id: "contact-name") }
        primaryMobile { $("input", id: "contact-primary-mobile")}
        notes { $("textarea", id: "contact-notes")}
        save { $("button", id: "contact-save") }
        cancel { $("button", id: "contact-create-cancel") }
        linkCaseButton { $("button", id: "link-case-button")}
        oneLinkCaseButtonExists { $("button", id: "link-case-button").size()==1}
        linkedCasesTable {
            $("#cases tbody tr").collect {module LinkedCaseRow, it}
        }
        saveWithoutNameDialog { $("div", id: "contact-save-no-name-dialog")}
        saveWithoutNameYes { $("button", id: "save-confirm-yes") }
        cancelYes { $("button", id: "contact-create-cancel-confirm") }
        cancelNo { $("button", id: "contact-create-cancel-abort") }
        linkCaseDialog {module LinkCaseDialog}
        caseContactRelationshipDialog { $("div", id: "case-contact-relationship-dialog")}
        relationshipInput { $("input", id: "case-contact-relationship")}
        relationshipConfirmButton { $("button", id: "confirm-relationship")}
        relationshipCancelButton { $("button", id: "cancel-relationship")}
    }
}

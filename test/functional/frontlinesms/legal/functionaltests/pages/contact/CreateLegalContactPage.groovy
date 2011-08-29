package frontlinesms.legal.functionaltests.pages.contact

import geb.Page
import geb.Module



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
        saveWithoutNameDialog { $("div", id: "contact-save-no-name-dialog")}
        saveWithoutNameYes { $("button", id: "save-confirm-yes") }
        cancelYes { $("button", id: "contact-create-cancel-confirm") }
        cancelNo { $("button", id: "contact-create-cancel-abort") }
        linkCaseDialog { $("div", id: "link-case-dialog")}
        caseIdSearch { $("input", id: "caseId") }
        searchResults {
            $("tbody tr").collect {module CaseRow, it}
        }
        linkCaseCancelButton { $(".ui-button-text").value("Cancel") }
    }
}

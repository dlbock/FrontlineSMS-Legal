package frontlinesms.legal.functionaltests.pages.contact

import geb.Module
import geb.Page

class ShowLegalContactPage extends Page {
    static at = { $("title").text() == "Show Contact Page"}
    static url = "legalContact/show"
    static content = {
        name { $("input", id: "contact-name").value() }
        primaryMobile { $("input", id: "contact-primary-mobile").value()}
        notes {$("textarea", id: "contact-notes")}
        linkCaseButton { $("button", id: "link-case-button")}
        pastEventsTable {$("#past-events tbody tr").collect {module EventRow, it} }
        futureEventsTable {$("#future-events tbody tr").collect {module EventRow, it} }
        deleteButton {$("button", id: "delete-button")}
        deleteDialog { $("div", id: "deleteDialog") }
        deleteYes { $("button", id: "confirm-yes")}
        deleteNo { $("button", id: "confirm-no")}
        casesToLink {
            try {
                $("tr[class='caseLink']")
            }
            catch (Exception e) {
                null
            }
        }
        currentEventsTable {$("#current-events tbody tr").collect {module EventRow, it} }
        caseIdSearch { $("input", id: "caseId") }
        caseLinkNotVisible { $("tr", class: "caseLink", filtermatch: "false").collect {module LegalContactRow, it} }
        clickDialogCancelButton { $(".ui-button-text").value("Cancel") }
    }
}
class EventRow extends Module {
    static content = {
        cell { i -> $("td", i) }
        title { cell(0).text() }
    }
}
class CaseRow extends Module {
    static content = {
        cell { i -> $("td", i) }
        caseId { $("span").text() }
        unlink { cell(2) }
    }
}

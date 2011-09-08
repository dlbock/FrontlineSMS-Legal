package frontlinesms.legal.functionaltests.pages.events

import frontlinesms.legal.functionaltests.pages.DateField
import geb.Page
import frontlinesms.legal.functionaltests.pages.LinkedCaseRow
import frontlinesms.legal.functionaltests.pages.LinkableCase
import frontlinesms.legal.functionaltests.pages.LinkContactDialog

class NewEventPage extends Page {
    static at = { $("title").text() == "Create New Event" }
    static url = "event/create"
    static content = {
        eventTitle { $("input", id: "event-title") }
        date { module DateField, $("input", id: "event-date") }
        save { $("input", id: "event-save") }
        cancel { $("button", id: "event-cancel") }
        errorMessage { $("div", id: "errorMessage").text() }
        eventCancelDialog { $("div", id: "event-cancel-dialog") }
        cancelYes { $("button", id: "cancel-confirm-yes") }
        cancelNo { $("button", id: "cancel-confirm-no") }
        startTimeField { $("input", id: "event-start-time") }
        endTimeField { $("input", id: "event-end-time") }
        contactDialogCancelButton { $("#cancel-link-contact") }
        linkCaseToEventButton { $("button", id: "link-case-button") }
        linkCaseDialog { $("div", id: "link-case-dialog") }
        caseDialogCancelButton { $("#cancel-link-case") }
        casesToLink { $("tr", class: "caseLink").collect {module LinkableCase, it} }
        oneContactIsDisplayed { $("#cases").size() == 1 }
        linkedCasesTable {
            $("#cases tbody tr").collect {module LinkedCaseRow, it}
            linkContactDialog { module LinkContactDialog }
            linkContact {
                $("button", id: "link-contact-button").click()
                true
            }
        }
    }
}
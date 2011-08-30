package frontlinesms.legal.functionaltests.pages.events

import geb.Module
import geb.Page

class NewEventPage extends Page {
    static at = { $("title").text() == "Create New Event" }
    static url = "event/create"
    static content = {
        eventTitle { $("input", id: "event-title") }
        dateFieldSelected {
            $("input", id: "event-date").click()
            waitFor(3) { datePicker.present }
        }
        datePicker { $("div", id: "ui-datepicker-div")}
        save { $("input", id: "event-save") }
        cancel { $("button", id: "event-cancel") }
        errorMessage { $("div", id: "errorMessage").text() }
        eventCancelDialog { $("div", id: "event-cancel-dialog")}
        cancelYes { $("button", id: "cancel-confirm-yes")}
        cancelNo { $("button", id: "cancel-confirm-no")}
        startTimeField { $("input", id: "event-start-time")}
        endTimeField { $("input", id: "event-end-time")}
        clickLinkContact {$("#link-contact-button")}
        linkContactButton { $("button", id: "link-contact-button")}
        contactsToLink {
            $("tr[class='contactLink']").collect { module LinkableContact, it }
        }
        linkContactDialog { $("div", id: "link-contacts") }
        contactNameSearch { $("input", id: "contact-name-search") }
        contactLinkNotVisible { $("tr", class: "contactLink", filtermatch: "false").collect {module ContactRow, it} }
        contactDialogCancelButton { $("#cancel-link-contact") }
        linkCaseButton {$("button", id: "link-case-button")}
        linkCaseDialog { $("div", id: "link-case-dialog") }
        caseDialogCancelButton { $("#cancel-link-case") }
        casesToLink { $("tr", class:"caseLink").collect {module LinkableCase, it} }
        linkedCasesTable{$("#cases")}
    }
}

class ContactRow extends Module {
    static content = {
        cell { i -> $("td", i) }
        name { cell(0).text() }
        primaryMobile { cell(1).text() }
    }
}

class LinkableContact extends Module {
    static content = {
        name { $(class: "contact-name").text() }
        number { $(class: "contact-number").text() }
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
